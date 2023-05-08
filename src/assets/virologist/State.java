package assets.virologist;

/** The states the field.virologist.Virologist can be in*/
public enum State implements java.io.Serializable{
    BEFORE_MOVE,
    BEFORE_ACTION,
    AFTER_ACTION,
    NOT_IN_TURN,
    KILLED
}