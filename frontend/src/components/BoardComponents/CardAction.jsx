import { buyProperty } from "../../services/CardActionServices";
import { useContext } from "react";
import { DiceRollContext } from "../../context/DiceRollContextProvider";

function CardAction({ board, player, setRefreshSquares }) {
  const {
    displayBuyAfterRoll,
    cardActionData,
    setCardActionData,
  } = useContext(DiceRollContext);

  return (
    <div>
      {board &&
        displayBuyAfterRoll &&
        board.currentPlayerTurn.id === player.id &&
        cardActionData.propertyPurcahseAction && (
          <button
            onClick={() =>
              buyProperty(
                setCardActionData
              )
            }
            className="roll-button"
          >
            buy for {cardActionData.price}
          </button>
        )}
    </div>
  );
}

export default CardAction;
