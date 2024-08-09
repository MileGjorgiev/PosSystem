import React from "react";
import "./Numpad.css";

const Numpad = ({ onNumberClick, onClear, onDelete }) => {
  const numbers = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9],
    ["C", 0, "Del"]
  ];

  return (
    <div className="numpad">
      {numbers.flat().map((num, index) => (
        <button
          key={index}
          className="numpad-button"
          onClick={() => {
            if (num === "C") {
              onClear();
            } else if (num === "Del") {
              onDelete();
            } else {
              onNumberClick(num);
            }
          }}
        >
          {num}
        </button>
      ))}
    </div>
  );
};

export default Numpad;