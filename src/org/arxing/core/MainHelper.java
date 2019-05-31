package org.arxing.core;

import org.arxing.core.utils.Args;
import org.arxing.core.utils.Logger;

import java.util.List;

public class MainHelper {

    public static void main(String[] argArray, Class<? extends IWorker>... workerTypes) throws Exception {
        Args args = Args.of(argArray);
        List<String> methods = args.popValues("-m");
        for (String method : methods) {
            IWorker worker = searchWorker(method, workerTypes);
            if (worker != null) {
                Logger.println("===> Run worker: %s", worker.name());
                worker.run(args);
            } else {
                Logger.println("===> Worker(%s) not defined!", method);
            }
        }
    }

    public static void main(String[] argArray, WorkerSearcher customSearcher) throws Exception {
        Args args = Args.of(argArray);
        List<String> methods = args.popValues("-m");
        for (String method : methods) {
            IWorker worker = customSearcher.search(method);
            if (worker != null) {
                Logger.println("===> Run worker: %s", worker.name());
                worker.run(args);
            } else {
                Logger.println("===> Worker(%s) not defined!", method);
            }
        }
    }

    private static IWorker searchWorker(String method, Class<? extends IWorker>... workerTypes) throws Exception {
        for (Class<? extends IWorker> workerType : workerTypes) {
            IWorker worker = workerType.newInstance();
            if (worker.name().equals(method))
                return worker;
        }
        return null;
    }

    public interface WorkerSearcher {

        IWorker search(String method) throws Exception;
    }
}
