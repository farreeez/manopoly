import { useEffect, useContext, useState } from "react";
import { BoardContext } from "../../context/BoardContextProvider";
import "./css/PropertyCard.css";
import { X } from "lucide-react";
import { AppContext } from "../../context/AppContextProvider";
import {
  getProperty,
  mortgageProperty,
  demortgageProperty,
  checkIfSetIsMortgaged,
  doesPropertyHaveHotel,
  buyHouse,
} from "../../services/CardActionServices";
import { getPlayerJson } from "../../services/PlayerServices";

export default function PropertyCard({ setDisplayProperty }) {
  // a boolean property used to check if any properties on the modalProperty's set have been mortgaged.
  const [ isSetMortgaged, setIsSetMortgaged ] = useState(false);
  const [ hasHotel, setHasHotel ] = useState(false);

  const { modalProperty, setModalProperty } = useContext(BoardContext);
  const { player, board } = useContext(AppContext);

  // Check if player owns the property
  const currentTurn = board.currentPlayerTurn.id == player.id;
  const isOwner = modalProperty.ownerId && modalProperty.ownerId === player.id 
  const canModifyProperty = isOwner && currentTurn;

  useEffect(() => {
    checkIfSetIsMortgaged(modalProperty.id, setIsSetMortgaged);
    doesPropertyHaveHotel(modalProperty.id, setHasHotel);
  }, [modalProperty])

  useEffect(() => {
    console.log("Modal Property:", modalProperty);
    console.log("House Cost:", modalProperty.houseCost);
    console.log("Player:", player);
  }, [modalProperty, player]);

  useEffect(() => {
    getProperty(modalProperty.id, setModalProperty);
  }, [board]);

  return (
    <div className="property-modal">
      <div
        className="property-overlay"
        onClick={() => setDisplayProperty(false)}
      >
        <div
          className="property-modal-content"
          onClick={(e) => e.stopPropagation()}
        >
          <button
            onClick={() => setDisplayProperty(false)}
            className="close-button"
          >
            <X size={24} />
          </button>

          <div className="propertyColour">{modalProperty.name}</div>

          <ul className="rent-list">
            {modalProperty.rentList.map((rent, index) => (
              <li key={index} className="rent-price">
                <span className="rent-text">{rent.rentPrompt}</span>
                <span className="rent-val">${rent.rentPrice}</span>
              </li>
            ))}

            {modalProperty.houseCost > 0 && (
              <div className="houses-indicator">
                {[...Array(4)].map((_, i) => (
                  <div
                    key={i}
                    className={`house ${
                      i < modalProperty.houses ? "active" : ""
                    }`}
                  ></div>
                ))}
                <div
                  className={`house ${
                    modalProperty.houses === 5 ? "hotel" : ""
                  }`}
                ></div>
              </div>
            )}

            <li key="price" className="rent-price">
              <span className="rent-text">Property Price</span>
              <span className="rent-val">${modalProperty.price}</span>
            </li>

            {!modalProperty.mortgaged ? (
              <li key="mortgage" className="rent-price">
                <span className="rent-text">
                  Mortgage for ${modalProperty.mortgagePayout}.
                </span>
                <button
                  className="button"
                  disabled={!canModifyProperty && !modalProperty.houseBuiltOnSet}
                  onClick={() => {
                    mortgageProperty(modalProperty.id);
                  }}
                >
                  Mortgage Property
                </button>
              </li>
            ) : (
              <li key="unmortgage" className="rent-price">
                <span className="rent-text">
                  Unmortgage for ${modalProperty.mortgageRepayment}.
                </span>
                <button
                  className="button unmortgage-button"
                  disabled={!canModifyProperty}
                  onClick={() => {
                    demortgageProperty(modalProperty.id);
                  }}
                >
                  Unmortgage Property
                </button>
              </li>
            )}

            {modalProperty.houseCost > 0 && (
              <li key="buy-house" className="rent-price">
                <span className="rent-text">
                  Buy House for ${modalProperty.houseCost}.
                </span>
                <button
                  className="button"
                  disabled={!canModifyProperty || !modalProperty.setOwned || isSetMortgaged || hasHotel}
                  onClick={() => {
                    buyHouse(modalProperty.id);
                  }}
                >
                  Buy House
                </button>
              </li>
            )}

            {modalProperty.houseCost > 0 && (
              <li key="sell-house" className="rent-price">
                <span className="rent-text">
                  Sell House for ${modalProperty.houseCost / 2}.
                </span>
                <button
                  className="button"
                  disabled={!canModifyProperty || modalProperty.houses === 0}
                >
                  Sell House
                </button>
              </li>
            )}
          </ul>
        </div>
      </div>
    </div>
  );
}
