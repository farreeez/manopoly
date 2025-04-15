import { getPlayerJson } from "./Board";

function CardAction({ board, player, cardActionData, setCardActionData, setRefreshSquares}) {
  function buyProperty() {
    fetch("http://localhost:8080/cardActions/buyProperty", {
      method: "POST",
      credentials: "include",
    })
      .then(async (response) => {
        if (!response.ok) {
          const message = await response.text();
          console.error("error with buying property:", message);
        }

        return response.json();
      })
      .then(async (data) => {
        setCardActionData(data);
        console.log(player)
        const playerData = await getPlayerJson(player.id);
        const currSquareId = Number(playerData.position);

        setRefreshSquares(prev => {
          const newRefreshSquares = [...prev];
          newRefreshSquares[currSquareId] = (prev[currSquareId] || 0) + 1;
          return newRefreshSquares; 
        });
      })
      .catch((error) => console.error(error));
  }
  return (
    <div>
      {board &&
        board.currentPlayerTurn.id === player.id &&
        cardActionData.propertyPurcahseAction && (
          <button onClick={() => buyProperty()} className="roll-button">
            buy for {cardActionData.price}
          </button>
        )}
    </div>
  );
}

export default CardAction;
