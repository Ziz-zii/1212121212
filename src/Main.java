//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    Object monitor = new Object();

    boolean[] isThread1Turn = {true}; // Т - 1. F - 2


    Thread thread1 = new Thread() {
        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                synchronized (monitor) {
                    try {
                        // Если сейчас не наша очередь - ждем
                        while (!isThread1Turn[0]) {
                            monitor.wait();
                        }

                        System.out.println(1);
                        isThread1Turn[0] = false;

                        monitor.notify();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    // Поток 2
    Thread thread2 = new Thread() {
        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                synchronized (monitor) {
                    try {
                        // Если сейчас не наша очередь - ждем
                        while (isThread1Turn[0]) {
                            monitor.wait();
                        }

                        // Выводим число
                        System.out.println(2);

                        // Меняем флаг на очередь потока 1
                        isThread1Turn[0] = true;

                        // Будим поток 1
                        monitor.notify();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    thread1.start();
    thread2.start();
}


