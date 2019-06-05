package org.arxing.core.comm;

public class WinCommand extends Command {

    public WinCommand() {
        super(new String[]{"cmd.exe", "/c"});
    }
}
