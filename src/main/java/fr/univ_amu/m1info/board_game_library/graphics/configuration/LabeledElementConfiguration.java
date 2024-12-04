package fr.univ_amu.m1info.board_game_library.graphics.configuration;

/**
 * Record representing the configuration of a labeled element in a board game.
 * It stores the label, an identifier, the type of the labeled element, and its position in the grid.
 *
 * @param label the text label of the element.
 * @param id the unique identifier for the element.
 * @param kind the type of the labeled element, represented by {@link LabeledElementKind}.
 * @param position the position and size of the element in the grid.
 */
    public record LabeledElementConfiguration(String label, String id, LabeledElementKind kind, Position position) {
        public LabeledElementConfiguration {
            if (label == null || id == null || kind == null || position == null) {
                throw new IllegalArgumentException("No parameter can be null");
            }
        }
    }