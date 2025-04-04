import "./css/Login.css";

function joinGame(player, setPlayer) {
  const textBox = document.getElementById("usernameInput");
  let name;

  if (textBox) {
    name = textBox.value;
    textBox.value = "";
  } else {
    alert("invalid text box id used in function joinGame in Login.tsx");
  }

  if (!name) {
    alert("please enter a valid name.");
    return;
  }

  fetch("http://localhost:8080/players/createPlayer/" + name, {
    method: "POST",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Could not create a new player.");
      }

      return response.json();
    })
    .then((data) => {
      console.log(Number(data.id))
      setPlayer({
        name: data.name,
        id: Number(data.id),
        isLoggedIn: true,
        boardId: -1,
        colour: data.colour,
      });
    })
    .catch((error) => console.error(error));
}

function Login({ player, setPlayer }) {
  return (
    <div id="login">
      <input type="text" id="usernameInput" placeholder="Enter Username"></input>
      <button id="loginButton" className="button" onClick={() => joinGame(player, setPlayer)}>
        Login
      </button>
    </div>
  );
}

export default Login;
