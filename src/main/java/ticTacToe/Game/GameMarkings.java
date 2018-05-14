package ticTacToe.Game;

public enum GameMarkings {
	CIRCLE {
		@Override
		public boolean isOpponentOf(GameMarkings s) {
			return s.equals(CROSS);
		}

		@Override
		public GameMarkings opponentOf() {
			return CROSS;
		}
	},
	CROSS {
		@Override
		public boolean isOpponentOf(GameMarkings s) {
			return s.equals(CIRCLE);
		}

		@Override
		public GameMarkings opponentOf() {
			return CIRCLE;
		}
	},
	EMPTY {
		@Override
		public boolean isOpponentOf(GameMarkings s) {
			return false;
		}

		@Override
		public GameMarkings opponentOf() {
			return this;
		}
	};

	public abstract boolean isOpponentOf(GameMarkings s);
	public abstract GameMarkings opponentOf();


}
