import "../App.css";
function joinBoard(player, setPlayer) {
  const textBox = document.getElementById("boardCodeTextBox");
  let boardCode;

  if (textBox) {
    boardCode = textBox.value;
    textBox.value = "";
  } else {
    alert("invalid text box id used in function joinBoard in HomePage.tsx");
  }

  if (!boardCode) {
    alert("please enter a valid name.");
    return;
  }

  fetch("http://localhost:8080/board/joinBoard/" + boardCode, {
    method: "POST",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Invalid Board Code.");
      }

      return response.json();
    })
    .then((data) => {

      let newPlayer = {
        name: player.name,
        id: Number(player.id),
        isLoggedIn: player.isLoggedIn,
        boardId: Number(data.id),
        colour: data.colour,
      };
      setPlayer(newPlayer);

      console.log(player.boardId)
    })
    .catch((error) => console.error(error));
}

function createBoard(player, setPlayer) {
  fetch("http://localhost:8080/board/createBoard", {
    method: "POST",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Could not create a new board.");
      }

      return response.json();
    })
    .then((data) => {
      let newPlayer = {
        name: player.name,
        id: Number(player.id),
        isLoggedIn: player.isLoggedIn,
        boardId: Number(data.id),
        colour: data.colour,
      };
      setPlayer(newPlayer);
    })
    .catch((error) => console.error(error));
}

function HomePage({ player, setPlayer }) {
  return (
    <div id="homePage">
      <h1 id="greetingPlayer">Hi {player.name}!</h1>
      <input type="text" id="boardCodeTextBox" placeholder="Enter Board Code"></input>
      <div id="buttonContainer">
        <button
          id="joinBoard"
          className="button"
          onClick={() => joinBoard(player, setPlayer)}
        >
          Join Board
        </button>
        <button
          id="createBoard"
          className="button"
          onClick={() => createBoard(player, setPlayer)}
        >
          Create New Board
        </button>
      </div>
    </div>
  );
}

export default HomePage;
