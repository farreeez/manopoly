package big.manopoly.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import big.manopoly.data.BoardRepository;
import big.manopoly.data.PlayerRepository;
import big.manopoly.data.PropertyRepository;
import big.manopoly.dtos.PropertyDTO;
import big.manopoly.dtos.TileActionDTO;
import big.manopoly.models.Board;
import big.manopoly.models.City;
import big.manopoly.models.Player;
import big.manopoly.models.Property;
import big.manopoly.utils.Mapper;
import big.manopoly.utils.PropertyUtils;
import big.manopoly.utils.TileActions;

@Service
public class PropertyService {

    private final BoardRepository boardRepository;
    private final PlayerRepository playerRepository;
    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(BoardRepository boardRepository, PlayerRepository playerRepository,
            PropertyRepository propertyRepository) {
        this.boardRepository = boardRepository;
        this.playerRepository = playerRepository;
        this.propertyRepository = propertyRepository;
    }

    public ResponseEntity<?> buyProperty(String playerIdCookie) {
        Player player;
        try {
            Long playerId = Long.valueOf(playerIdCookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid playerId cookie value");
        }

        Board board = player.getBoard();
        if (board == null) {
            return ResponseEntity.badRequest()
                    .body("player cannot buy the property as they are not currently in a game.");
        }

        if (player.getId() != board.getPlayerWithCurrentTurn().getId()) {
            return ResponseEntity.badRequest()
                    .body("player cannot buy the property as it is not currently their turn.");
        }

        try {
            PropertyUtils.buyPropertyFromBoard(player, playerRepository, boardRepository);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        TileActionDTO actionDTO;
        try {
            actionDTO = TileActions.conductTileAction(player, board, board.getDiceRolls());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().body(actionDTO);
    }

    public ResponseEntity<?> getProperty(String id) {
        Property property;
        try {
            property = propertyRepository.getReferenceById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        PropertyDTO propertyDTO = Mapper.toPropertyDTO(property);
        return ResponseEntity.ok().body(propertyDTO);
    }

    private ResponseEntity<?> handleMortgageOperation(String propertyId, String playerIdCookie, boolean isMortgaging) {
        Player player;

        try {
            Long playerId = Long.valueOf(playerIdCookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid playerId cookie value");
        }

        Board board = player.getBoard();
        if (board == null) {
            return ResponseEntity.badRequest()
                    .body("player cannot " + (isMortgaging ? "mortgage" : "demortgage") +
                            " the property as they are not currently in a game.");
        }

        Property property;
        try {
            property = propertyRepository.getReferenceById(propertyId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        ResponseEntity<?> validationResponse = validateMortgageOperation(player, property, board, isMortgaging);
        if (validationResponse != null) {
            return validationResponse;
        }

        if (isMortgaging) {
            property.setMortgaged(true);
            player.addMoney(property.getMortgagePayout());
        } else {
            property.setMortgaged(false);
            player.pay(property.getMortgageCost());
        }

        playerRepository.save(player);
        propertyRepository.save(property);
        BoardSubscriptionManager.instance().processSubsFor(board.getId(), boardRepository, false, -1);

        return ResponseEntity.ok().build();
    }

    private ResponseEntity<?> validateMortgageOperation(Player player, Property property, Board board,
            boolean isMortgaging) {
        if (property.getOwner() == null || (property.getOwner() != player)) {
            return ResponseEntity.badRequest().body("property is not owned by the player.");
        }

        if (player.isHouseBuiltOnSet(property.getType())) {
            return ResponseEntity.badRequest().body("cannot " + (isMortgaging ? "mortgage" : "demortgage") +
                    " property as houses are built on the set.");
        }

        if (board.getPlayerWithCurrentTurn() != player) {
            return ResponseEntity.badRequest().body("cannot " + (isMortgaging ? "mortgage" : "demortgage") +
                    " the property as it is not the player's current turn.");
        }

        boolean currentlyMortgaged = property.isMortgaged();
        if (isMortgaging && currentlyMortgaged) {
            return ResponseEntity.badRequest().body("cannot mortgage property as it is already mortgaged.");
        } else if (!isMortgaging && !currentlyMortgaged) {
            return ResponseEntity.badRequest().body("current command cannot be done as it is already applied.");
        }

        return null;
    }

    public ResponseEntity<?> mortgageProperty(String propertyId, String playerIdCookie) {
        return handleMortgageOperation(propertyId, playerIdCookie, true);
    }

    public ResponseEntity<?> demortgageProperty(String propertyId, String playerIdCookie) {
        return handleMortgageOperation(propertyId, playerIdCookie, false);
    }

    private boolean isSetMortgagedHelper(Property property) {
        List<Property> propertySet = propertyRepository.findByTypeAndBoard(property.getType(), property.getBoard());

        for (int i = 0; i < propertySet.size(); i++) {
            if (propertySet.get(i).isMortgaged()) {
                return true;
            }
        }

        return false;
    }

    public ResponseEntity<?> isSetMortgaged(String id) {
        Property property;

        try {
            property = propertyRepository.getReferenceById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(isSetMortgagedHelper(property));
    }

    public boolean doesPropertyHaveHotelHelper(Property property) {
        property = (Property) Hibernate.unproxy(property);

        if (property instanceof City) {
            City city = (City) property;

            return city.getHouses() >= 5;
        }

        return false;
    }

    public ResponseEntity<?> doesPropertyHaveHotel(String id) {
        Property property;

        try {
            property = propertyRepository.getReferenceById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(doesPropertyHaveHotelHelper(property));
    }

    private boolean areHousesEven(Property property) {
        List<Property> propertySet = propertyRepository.findByTypeAndBoard(property.getType(), property.getBoard());

        int largestHouseCount = -1;
        int smallestHouseCount = 6;

        for (int i = 0; i < propertySet.size(); i++) {
            City city = (City) Hibernate.unproxy(propertySet.get(i));

            int houseCount = city.getHouses();

            if (houseCount < smallestHouseCount) {
                smallestHouseCount = houseCount;
            }

            if (houseCount > largestHouseCount) {
                largestHouseCount = houseCount;
            }
        }

        return !(largestHouseCount > smallestHouseCount + 1);
    }

    private boolean areHousesEvenToBuy(Property property) {
        List<Property> propertySet = propertyRepository.findByTypeAndBoard(property.getType(), property.getBoard());

        City currentCity = (City) Hibernate.unproxy(property);
        int currHouses = currentCity.getHouses();

        for (int i = 0; i < propertySet.size(); i++) {
            City city = (City) Hibernate.unproxy(propertySet.get(i));

            int houseCount = city.getHouses();

            if (houseCount < currHouses) {
                return false;
            }
        }

        return true;
    }

    public ResponseEntity<?> buyHouse(String propertyId, String playerIdCookie) {
        Player player;

        try {
            Long playerId = Long.valueOf(playerIdCookie);
            player = playerRepository.getReferenceById(playerId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid playerId cookie value");
        }

        Board board = player.getBoard();
        if (board == null) {
            return ResponseEntity.badRequest().build();
        }

        if (!board.getPlayerWithCurrentTurn().equals(player)) {
            return ResponseEntity.badRequest().body("Cannot build a property when it is not the player's turn.");
        }

        Property property;
        try {
            property = propertyRepository.getReferenceById(propertyId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        property = (Property) Hibernate.unproxy(property);

        if (!(property instanceof City)) {
            return ResponseEntity.badRequest()
                    .body("The property should be of type city for the player to be able to build a house on it.");
        }

        if (!areHousesEven(property)) {
            return ResponseEntity.badRequest().body("Houses are unevenly built on this set.");
        }

        if (!areHousesEvenToBuy(property)) {
            return ResponseEntity.badRequest().body(
                    "Cannot buy a house on this city as it will make the distribution of houses uneven on this set.");
        }

        boolean hasSet = player.doesOwnSet(property.getType());

        if (!hasSet) {
            return ResponseEntity.badRequest().body("player does not own the property set.");
        }

        if (isSetMortgagedHelper(property)) {
            return ResponseEntity.badRequest()
                    .body("one of the properties is mortgaged the player cannot build on this set.");
        }

        if (doesPropertyHaveHotelHelper(property)) {
            return ResponseEntity.badRequest().body("the property already has the maximum number of houses allowed.");
        }

        City city = (City) Hibernate.unproxy(property);
        
        if(player.getMoney() < city.getHouseCost()) {
            return ResponseEntity.badRequest().body("player cannot afford this property.");
        }

        player.pay(city.getHouseCost());

        city.addHouse();

        playerRepository.save(player);
        propertyRepository.save(property);

        BoardSubscriptionManager.instance().processSubsFor(board.getId(), boardRepository, false, -1);

        return ResponseEntity.ok().build();
    }
}