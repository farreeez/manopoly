export function createPlayer(name, setPlayer) {
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

export async function getPlayerJson(playerId) {
    try {
      const response = await fetch(
        `http://localhost:8080/players/getPlayer/${playerId}`,
        {
          method: "GET",
        }
      );
  
      if (!response.ok) {
        throw new Error("player id does not exist in function getPlayerJson.");
      }
  
      const data = await response.json();
      return data;
    } catch (error) {
      console.error(error);
      throw error; // Re-throw the error if you want the caller to handle it
    }
  }
