import { buyProperty } from "../../services/CardActionServices";

function CardAction({
  board,
  player,
  cardActionData,
  setCardActionData,
  setRefreshSquares,
}) {
  return (
    <div>
      {board &&
        board.currentPlayerTurn.id === player.id &&
        cardActionData.propertyPurcahseAction && (
          <button
            onClick={() =>
              buyProperty(player, setCardActionData, setRefreshSquares)
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
