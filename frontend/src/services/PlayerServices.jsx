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

export function checkCookie(setPlayer) {
  fetch("http://localhost:8080/players/checkCookie", {
    method: "GET",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        if (document.cookie) {
          // Get all cookies and split into array
          const cookies = document.cookie.split(";");

          // Clear all cookies
          for (let cookie of cookies) {
            const cookieName = cookie.split("=")[0].trim();
            document.cookie = `${cookieName}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
          }
        }
        throw new Error("Could not create a new player.");
      }

      return response.text();
    })
    .then((data) => {
      if (data) {
        data = JSON.parse(data);

        let newPlayer = {
          name: data.name,
          id: Number(data.id),
          isLoggedIn: true,
          boardId: Number(data.boardId),
          colour: data.colour,
        };

        setPlayer(newPlayer);
      }
    })
    .catch((error) => console.error(error));
}

export function payJailFine() {
  fetch("http://localhost:8080/players/payJailFine" , {
    method: "POST",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        return response.text().then((text) => {
          throw new Error(`Cannot pay jail fine: ${text}`);
        });
      }
    })
    .catch((error) => console.error(error));
}
