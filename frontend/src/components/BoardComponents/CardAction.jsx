import { buyProperty } from "../../services/CardActionServices";
import { useContext, useEffect } from "react";
import { DiceRollContext } from "../../context/DiceRollContextProvider";
import { AppContext } from "../../context/AppContextProvider";
import { getPlayerJson } from "../../services/PlayerServices";

function CardAction({ board, player, setRefreshSquares }) {
  const { displayBuyAfterRoll, cardActionData, setCardActionData } =
    useContext(DiceRollContext);

  const { playerDTO, setPlayerDTO } = useContext(AppContext);

  useEffect(() => {
    const updatePlayerDTO = async () => {
      setPlayerDTO(await getPlayerJson(player.id));
    };

    updatePlayerDTO();
  }, []);

  return (
    <div>
      {board &&
        displayBuyAfterRoll &&
        board.currentPlayerTurn.id === player.id &&
        cardActionData.propertyPurcahseAction &&
        playerDTO.free && (
          <button
            onClick={() => buyProperty(setCardActionData)}
            className="roll-button"
            disabled={playerDTO.money < cardActionData.price}
          >
            buy for {cardActionData.price}
          </button>
        )}

      {board && board.currentPlayerTurn.id === player.id && !playerDTO.free && (
        <button className="roll-button">Pay 50$</button>
      )}
    </div>
  );
}

export default CardAction;
