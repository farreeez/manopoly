function leaveBoard(player, setPlayer) {
  fetch("http://localhost:8080/board/leaveBoard", {
    method: "DELETE",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Player does not exist.");
      }
      return response;
    })
    .then(() => {
      setPlayer({
        name: player.name,
        id: Number(player.id),
        isLoggedIn: true,
        boardId: -1,
      });
    })
    .catch((error) => console.error(error));
}

function Board({ player, setPlayer }) {
  return (
    <div>
      <div id="headerContainer">
        <button
          id="leaveButton"
          className="button"
          onClick={() => leaveBoard(player, setPlayer)}
        >
          Leave Board
        </button>
        <h1 id="boardId">Board Code: {player.boardId}</h1>
      </div>
    </div>
  );
}

export default Board;
