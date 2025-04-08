function CardAction({ board, player, cardActionData, setCardActionData}) {
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
      .then((data) => {
        setCardActionData(data);
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
