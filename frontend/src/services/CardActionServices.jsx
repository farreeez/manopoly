export function getProperty(propertyId, setProperty) {
  fetch("http://localhost:8080/cardActions/getProperty/" + propertyId, {
    method: "get",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("board does not exist.");
      }
      return response.json();
    })
    .then((data) => {
      setProperty(data);

      if (data.colour) {
        console.log(data.colour);
      }
    })
    .catch((error) => console.error(error));
}

export function buyProperty(setCardActionData) {
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
    .then(async (data) => {
      setCardActionData(data);
    })
    .catch((error) => console.error(error));
}
