package events;

import constant.UpdaterConstant;

import javax.swing.*;
import java.util.EventListener;

public class ProcessOverrideFileListenerImpl implements ProcessOverrideFileListener {
    private String msg;
    JDialog dialog = new JDialog();
    JOptionPane optionPane;

    public ProcessOverrideFileListenerImpl(String msg) {
        this.msg = msg;
    }

    @Override
    public void onEvent() {
        optionPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        dialog.setUndecorated(true);
        dialog.setSize(200, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        dialog.setVisible(true);
    }

    @Override
    public void onDestroy() {
        System.out.println("on destroy");
        dialog.dispose();
        dialog = null;
    }
}
