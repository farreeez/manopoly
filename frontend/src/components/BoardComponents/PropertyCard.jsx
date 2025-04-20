import { useEffect, useContext } from "react";
import { BoardContext } from "../../context/BoardContextProvider";
import "./css/PropertyCard.css";
import { X } from "lucide-react";

export default function PropertyCard({ setDisplayProperty, property }) {
  const { modalProperty, setModalProperty } = useContext(BoardContext);

  useEffect(() => {
    console.log(modalProperty);
    console.log(modalProperty.houseCost);
  }, [modalProperty]);

  return (
    <div className="property-modal">
      <div
        className="property-overlay"
        onClick={() => {
          setDisplayProperty(false);
        }}
      >
        <div
          className="property-modal-content"
          onClick={(e) => {
            e.stopPropagation();
          }}
        >
          <button
            onClick={() => {
              setDisplayProperty(false);
            }}
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
                <button className="button">Mortgage Property</button>
              </li>
            ) : (
              <li key="unmortgage" className="rent-price">
                <span className="rent-text">
                  Unmortgage for ${modalProperty.mortgageRepayment}.
                </span>
                <button className="button">Unmortgage Property</button>
              </li>
            )}

            {modalProperty.houseCost > 0 && (
              <li key="buy-house" className="rent-price">
                <span className="rent-text">
                  Buy House for ${modalProperty.houseCost}.
                </span>
                <button className="button">Buy House</button>
              </li>
            )}

            {modalProperty.houseCost > 0 && (
              <li key="sell-house" className="rent-price">
                <span className="rent-text">
                  Sell House for ${modalProperty.houseCost / 2}.
                </span>
                <button className="button">Sell House</button>
              </li>
            )}
          </ul>
        </div>
      </div>
    </div>
  );
}
