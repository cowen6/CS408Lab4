package edu.jsu.mcis.cs408.memopad;

public class MemoPadController extends AbstractController {

    private final MemoPadModel model;

    public static final String ELEMENT_MEMO_LIST = "Memos";

    public MemoPadController(MemoPadModel model) {
        super();
        this.model = model;
        addModel(model);
    }

    public void addNewMemo(String newMemo) {
        Memo memo = new Memo(newMemo);
        model.addNewMemo(memo);
    }

    public void deleteMemo() {
        model.deleteMemo();
    }

    public void listAllMemos() {
        model.listMemos();
    }

    public void setDeleteId(int id) {
        model.setDeleteId(id);
    }

}
