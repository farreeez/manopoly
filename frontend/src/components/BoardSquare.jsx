function BoardSquare({ player, setPlayer, width, height, squareId }) {
  console.log(squareId);

  return (
    <div className="boardSquare" style={{ width: width, height: height }}></div>
  );
}

export default BoardSquare;
