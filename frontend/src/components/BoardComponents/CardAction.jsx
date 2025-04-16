import { buyProperty } from "../../services/CardActionServices";
import { useContext } from "react";
import { DiceRollContext } from "../../context/DiceRollContextProvider";
import { AppContext } from "../../context/AppContextProvider";

function CardAction({ board, player, setRefreshSquares }) {
  const { displayBuyAfterRoll, cardActionData, setCardActionData } =
    useContext(DiceRollContext);

  const { playerDTO, setPlayerDTO } = useContext(AppContext);

  return (
    <div>
      {board &&
        displayBuyAfterRoll &&
        board.currentPlayerTurn.id === player.id &&
        cardActionData.propertyPurcahseAction && (
          <button
            onClick={() => buyProperty(setCardActionData)}
            className="roll-button"
            disabled={playerDTO.money < cardActionData.price}
          >
            buy for {cardActionData.price}
          </button>
        )}
    </div>
  );
}

export default CardAction;
