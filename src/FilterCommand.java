public class FilterCommand implements CommandController{
    private final String filterName;
    private final ImageModel imageModel;

    public FilterCommand(ImageModel imageModel, String filterName) {
        this.imageModel = imageModel;
        this.filterName = filterName;
    }
    @Override
    public void execute(String[] args) {
        String sourceImageName = args[1];
        String destImageName = args[2];
        imageModel.applyFilter(sourceImageName, destImageName, filterName);
    }
}
