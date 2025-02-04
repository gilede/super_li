package DataAccessLayer;

public abstract class DTO {
    protected DbController _controller;

    public DTO(DbController controller) {
        this._controller = controller;
    }

}
