package rccookie.geometry;

public class Transform3D implements Cloneable {

    public static final String SAVE_DIR = "saves\\geometry\\transforms";

    private static final long serialVersionUID = -7384556436740912937L;

    /**
     * Do not set to {@code null}!
     */
    public Vector3D location;

    /**
     * Do not set to {@code null}!
     */
    public Rotation rotation;

    /**
     * Do not set to {@code null}!
     */
    public Vector3D scale;


    public Transform3D() {
        this(new Vector3D(), new Rotation(), new Vector3D(1, 1, 1));
    }
    public Transform3D(Transform3D copy) {
        this(
            copy.location.clone(),
            copy.rotation.clone(),
            copy.scale.clone()
        );
    }
    public Transform3D(Vector3D location) {
        this(location, new Rotation(), new Vector3D(1, 1 ,1));
    }
    public Transform3D(Vector3D location, Rotation rotation, Vector3D scale) {
        this.location = location;
        this.rotation = rotation;
        this.scale = scale;
    }



    @Override
    public Transform3D clone() {
        return new Transform3D(this);
    }
}
