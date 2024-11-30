import "../App.css";
function joinBoard(setPlayer) {
  const textBox = document.getElementById("boardId");
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
      console.log(data)

      let newPlayer = {name: player.name, id: player.id, isLoggedIn: player.isLoggedIn, boardId: data.id}
      setPlayer(newPlayer)
    })
    .catch((error) => console.error(error));
}


function HomePage({player, setPlayer}) {
  return (
    <div id="homePage">
      <h1 id="greetingPlayer">Hi {player.name}!</h1>
      <input type="text" id="boardId" placeholder="Enter Board Code"></input>
      <div id="buttonContainer">
        <button id="joinBoard" onClick={() => joinBoard(setPlayer)}>Join Board</button>
        <button id="createBoard" onClick = {() => createBoard(player, setPlayer)}>Create New Board</button>
      </div>
    </div>
  );
}

export default HomePage;
