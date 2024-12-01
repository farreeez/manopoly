import BoardSquare from "./BoardSquare";

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
  let squares = [];
  for (let i = 0; i < 40; i++) {
    squares.push(i);
  }

  // TODO: make a better solution to draw the board
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

      <ul id="topRowBoardSquares">
        <li key={0}>
          <BoardSquare width={"90px"} height={"90px"} />
        </li>
        {squares.slice(1, 10).map((square, index) => (
          <li key={index}>
            <BoardSquare width={"70px"} height={"90px"} />
          </li>
        ))}
        <li key={10}>
          <BoardSquare width={"90px"} height={"90px"} />
        </li>
      </ul>

      <ul id="rightColBoardSquares">
        {squares.slice(11, 20).map((square, index) => (
          <li key={index}>
            <BoardSquare width={"90px"} height={"70px"} />
          </li>
        ))}
      </ul>

      <ul id="bottomRowBoardSquares">
        <li key={20}>
          <BoardSquare width={"90px"} height={"90px"} />
        </li>
        {squares.slice(21, 30).reverse().map((square, index) => (
          <li key={index}>
            <BoardSquare width={"70px"} height={"90px"} />
          </li>
        ))}
        <li key={30}>
          <BoardSquare width={"90px"} height={"90px"} />
        </li>
      </ul>

      <ul id="leftColBoardSquares">
        {squares.slice(31, 40).map((square, index) => (
          <li key={index}>
            <BoardSquare width={"90px"} height={"70px"} />
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Board;
