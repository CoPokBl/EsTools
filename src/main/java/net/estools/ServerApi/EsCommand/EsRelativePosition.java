package net.estools.ServerApi.EsCommand;

import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Position;

public abstract class EsRelativePosition {
    private final Position position;
    private RelativeType relativeTypeX;
    private RelativeType relativeTypeY;
    private RelativeType relativeTypeZ;

    public EsRelativePosition() {
        this(new Position());
    }

    public EsRelativePosition(Position position) {
        this.position = position;
        relativeTypeX = RelativeType.GLOBAL;
        relativeTypeY = RelativeType.GLOBAL;
        relativeTypeZ = RelativeType.GLOBAL;
    }

    public Position position() {
        return position.copy();
    }

    public double x() {
        return position.getX();
    }

    public double y() {
        return position.getY();
    }

    public double z() {
        return position.getZ();
    }

    public void setX(double x) {
        position.setX(x);
    }

    public void setY(double y) {
        position.setY(y);
    }

    public void setZ(double z) {
        position.setZ(z);
    }

    public RelativeType getRelativeTypeX() {
        return relativeTypeX;
    }

    public void setRelativeTypeX(RelativeType relativeTypeX) {
        this.relativeTypeX = relativeTypeX;
    }

    public RelativeType getRelativeTypeY() {
        return relativeTypeY;
    }

    public void setRelativeTypeY(RelativeType relativeTypeY) {
        this.relativeTypeY = relativeTypeY;
    }

    public RelativeType getRelativeTypeZ() {
        return relativeTypeZ;
    }

    public void setRelativeTypeZ(RelativeType relativeTypeZ) {
        this.relativeTypeZ = relativeTypeZ;
    }

    public RelativeType getRelativeType(int index) {
        switch (index) {
            case 0: return getRelativeTypeX();
            case 1: return getRelativeTypeY();
            case 2: return getRelativeTypeZ();
        }

        throw new IndexOutOfBoundsException();
    }

    public void setRelativeType(int index, RelativeType relativeType) {
        switch (index) {
            case 0: setRelativeTypeX(relativeType); return;
            case 1: setRelativeTypeY(relativeType); return;
            case 2: setRelativeTypeZ(relativeType); return;
        }

        throw new IndexOutOfBoundsException();
    }

    public double getPos(int index) {
        return getPositionPos(position, index);
    }

    protected double getPositionPos(Position vector, int index) {
        switch (index) {
            case 0: return vector.getX();
            case 1: return vector.getY();
            case 2: return vector.getZ();
        }

        throw new IndexOutOfBoundsException();
    }

    public void setPos(int index, double pos) {
        setPositionPos(position, index, pos);
    }

    protected void setPositionPos(Position vector, int index, double pos) {
        switch (index) {
            case 0: vector.setX(pos); return;
            case 1: vector.setY(pos); return;
            case 2: vector.setZ(pos); return;
        }

        throw new IndexOutOfBoundsException();
    }

    public abstract Position fromSender(EsCommandSender sender);

    public enum RelativeType {
        GLOBAL,
        SENDER,
        SENDER_LOOK
    }
}
