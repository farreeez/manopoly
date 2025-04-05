function CardAction({ board, player, cardActionData }) {
  return (
    <div>
      {board && board.currentPlayerTurn.id === player.id && (
        <button className="roll-button">card action</button>
      )}
    </div>
  );
}

export default CardAction;
