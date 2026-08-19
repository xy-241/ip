public enum Command {
    LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, BYE, UNKNOWN;

    public static Command fromInput(String input) {
        String head = input.split(" ", 2)[0];
        try {
            return Command.valueOf(head.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
