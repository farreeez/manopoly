import { error } from "console";
import "../App.css";

function joinGame() {
    const textBox = document.getElementById("usernameInput") as HTMLInputElement | null;


    let name;
    if(textBox) {
        name = textBox.value;
        textBox.value = "";
    } else {
        alert("invalid text box id used in function joinGame in Login.tsx");
    }

    if(!name) {
        alert("please enter a valid name.");
        return;
    }

    fetch("http://localhost:8080/players/createPlayer/" + name, {
      method: "POST",
    })
    .then(response => {
        if(!response.ok) {
            throw new Error("Could not create a new player.");
        }

        return response.json();
    })
    .then((data) => console.log(data))
    .catch(error => console.error(error));
  }

function Login() {
    return (
    <div id="login">
        <input type="text" id="usernameInput"></input>
        <button id="loginButton" onClick={joinGame}>Login</button>
    </div>
    );
}

export default Login;