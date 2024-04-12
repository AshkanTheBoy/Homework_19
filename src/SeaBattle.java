
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeaBattle {
    static int[][] sea = new int[4][6];

    public static void main(String[] args) {
        makeSea();
        System.out.println("TURNS COMPLETED: " + autoBattle());
//        fireAt(1,2);
//        fireAt(1,1);
//        fireAt(2,2);
    }

    public static void printSea() {
        for (int[] line : sea) {
            System.out.println(Arrays.toString(line));
        }
    }

    public static void makeSea() {
        sea[0][0] = 1;
        sea[1][0] = 1;

        sea[0][4] = 1;
        sea[0][5] = 1;

        sea[2][5] = 1;
        sea[3][5] = 1;

        sea[3][1] = 1;
        sea[3][0] = 1;
    }

    //стреляем по координатам "1-6","1-4"
    public static int fireAt(int xCord, int yCord) {
        //переводим координаты под массив
        xCord--;
        yCord--;
        //границы массива
        int yEdge = sea.length - 1;
        int xEdge = sea[yCord].length - 1;
        int count = 0; //длина корабля, 0 - когда клетка "пустая"
        switch (sea[yCord][xCord]) { //поведение по клетке-цели
            case 1: { //если есть корабль и он под защитой
                if (isShipHrz(xCord, yCord)) { //если корабль горизонтальный
                    List<Integer> cords = getHShipCords(xCord, yCord); //берем х-координаты его частей
                    for (int cord : cords) {
                        sea[yCord][cord] = 2; //отключаем защиту у корабля
                    }
                } else { //корабль вертикальный
                    List<Integer> cords = getVShipCords(xCord, yCord); // берем у-координаты частей
                    for (int cord : cords) {
                        sea[cord][xCord] = 2; //отключаем защиту
                    }
                }
                break;
            }
            case 2: { //если есть корабль, но без защиты
                if (isShipHrz(xCord, yCord)) { //корабль горизонтальный
                    List<Integer> cords = getHShipCords(xCord, yCord);

                    //определяем "концы" корабля
                    int lastCell = cords.get(cords.size() - 1);
                    int firstCell = cords.get(0);

                    count += checkHrz(xCord, yCord); //считаем части корабля

                    //поведение под местоположение корабля
                    if (yCord == 0) { //если корабль вверху

                        //помечаем корабль и местность, как "открытые"
                        for (int cord : cords) {
                            sea[yCord][cord] = 3;
                            //помечаем соседние клетки в зависимости от местоположения данной

                            //если правая крайняя не на границе
                            if (cord == lastCell && lastCell != xEdge) {
                                sea[yCord + 1][cord] = 3; //вниз
                                sea[yCord][cord + 1] = 3; //вправо
                                sea[yCord + 1][cord + 1] = 3; //вниз-вправо

                                //если левая крайняя не на границе
                            } else if (cord == firstCell && firstCell != 0) {
                                sea[yCord][cord - 1] = 3; //влево
                                sea[yCord + 1][cord - 1] = 3; //вниз-влево
                                sea[yCord + 1][cord] = 3; //вниз

                            } else { //у всех остальных
                                sea[yCord + 1][cord] = 3; //вниз
                            }
                        }
                    } else if (yCord == yEdge) { //корабль снизу
                        for (int cord : cords) {
                            sea[yCord][cord] = 3;

                            //правая крайняя не на границе
                            if (cord == lastCell && lastCell != xEdge) {
                                sea[yCord - 1][cord] = 3; //вниз
                                sea[yCord][cord + 1] = 3; //влево
                                sea[yCord - 1][cord + 1] = 3; //вниз-влево

                                //левая крайняя не на границе
                            } else if (cord == firstCell && firstCell != 0) {
                                sea[yCord][cord - 1] = 3; //влево
                                sea[yCord - 1][cord - 1] = 3; //вниз-влево
                                sea[yCord - 1][cord] = 3; //вниз

                            } else { //у всех остальных
                                sea[yCord - 1][cord] = 3; //вверх
                            }
                        }

                        //корабль между границами
                    } else {
                        for (int cord : cords) {
                            sea[yCord][cord] = 3;

                            //правая крайняя не на границе
                            if (cord == lastCell && lastCell != xEdge) {
                                sea[yCord - 1][cord] = 3; //вниз
                                sea[yCord + 1][cord] = 3; //вверх

                                sea[yCord][cord + 1] = 3; //вправо

                                sea[yCord - 1][cord + 1] = 3; //вниз-вправо
                                sea[yCord + 1][cord + 1] = 3; //вверх-вправо

                                //левая крайняя не на границе
                            } else if (cord == firstCell && firstCell != 0) {
                                sea[yCord][cord - 1] = 3; //влево

                                sea[yCord - 1][cord - 1] = 3; //вниз-влево
                                sea[yCord + 1][cord - 1] = 3; //вверх-влево

                                sea[yCord - 1][cord] = 3; //вниз
                                sea[yCord + 1][cord] = 3; //вверх

                            } else { //у всех остальных
                                sea[yCord + 1][cord] = 3; //вниз
                                sea[yCord - 1][cord] = 3; //вверх
                            }
                        }
                    }

                } else { //корабль вертикальный
                    List<Integer> cords = getVShipCords(xCord, yCord); //у-координаты частей
                    //границы корабля
                    int lastCell = cords.get(cords.size() - 1);
                    int firstCell = cords.get(0);
                    count += checkVrt(xCord, yCord); //считаем части

                    if (xCord == 0) { //корабль на левой границе
                        for (int cord : cords) {
                            sea[cord][xCord] = 3;
                            //нижняя часть не на границе
                            if (cord == lastCell && lastCell != yEdge) {
                                sea[cord][xCord + 1] = 3; //вправо
                                sea[cord + 1][xCord] = 3; //вниз
                                sea[cord + 1][xCord + 1] = 3; //вниз-вправо

                                //верхняя часть не на границе
                            } else if (cord == firstCell && firstCell != 0) {
                                sea[cord - 1][xCord] = 3; //вверх
                                sea[cord - 1][xCord + 1] = 3; //вверх-вправо
                                sea[cord][xCord + 1] = 3; // вправо

                            } else { //у всех остальных
                                sea[cord][xCord + 1] = 3; //вправо
                            }
                        }

                        //корабль на правой границе
                    } else if (xCord == xEdge) {
                        for (int cord : cords) {
                            sea[cord][xCord] = 3;

                            //нижняя часть не на границе
                            if (cord == lastCell && lastCell != yEdge) {
                                sea[cord + 1][xCord] = 3; //вниз
                                sea[cord + 1][xCord - 1] = 3; //вниз-влево
                                sea[cord][xCord - 1] = 3; //влево

                                //верхняя часть не на границе
                            } else if (cord == firstCell && firstCell != 0) {
                                sea[cord - 1][xCord] = 3; //вверх
                                sea[cord - 1][xCord - 1] = 3; //вверх-влево
                                sea[cord][xCord - 1] = 3; //влево

                            } else { //у всех остальных
                                sea[cord][xCord - 1] = 3; //влево
                            }
                        }
                        //корабль не на границах
                    } else {
                        for (int cord : cords) {
                            sea[cord][xCord] = 3;

                            //нижняя часть не на границе
                            if (cord == lastCell && lastCell != yEdge) {
                                sea[cord + 1][xCord] = 3; //вниз

                                sea[cord + 1][xCord - 1] = 3; //вниз-влево
                                sea[cord + 1][xCord + 1] = 3; //вниз-вправо

                                sea[cord][xCord - 1] = 3; //влево
                                sea[cord][xCord + 1] = 3; //вправо

                                //верхняя часть не на границе
                            } else if (cord == firstCell && firstCell != 0) {
                                sea[cord - 1][xCord] = 3; //вверх

                                sea[cord - 1][xCord - 1] = 3; //вверх-влево
                                sea[cord - 1][xCord + 1] = 3; //вверх-вправо

                                sea[cord][xCord - 1] = 3; //влево
                                sea[cord][xCord + 1] = 3; //вправо

                            } else { //у всех остальных
                                sea[cord][xCord - 1] = 3; //влево
                                sea[cord][xCord + 1] = 3; //вправо
                            }
                        }
                    }
                }
                break;
            }
            case 3: { //3 - обломки, которыми помечен корабль, и местность вокруг на расстоянии 1 кл.
                count = -1; //вернем -1
            }
        }

        //эта часть нужна для "автобоя", чтобы не выводить поле в консоль и создать иллюзию эффективности
        if (count != -1) { //если мы не на обломках - выводим поле в консоль
            printSea();
            System.out.println();
        }
        return count; //возвращаем "инфу" по кораблю
    }

    public static boolean isShipHrz(int xCord, int yCord) {
        //просто проверяем левую и правую от нашей клетки
        if (xCord <= 0) {
            return sea[yCord][xCord + 1] == 1 || sea[yCord][xCord + 1] == 2;
        } else if (xCord >= sea[yCord].length - 1) {
            return sea[yCord][xCord - 1] == 1 || sea[yCord][xCord - 1] == 2;
        } else {
            return (sea[yCord][xCord - 1] == 1) ^ (sea[yCord][xCord - 1] == 2) ||
                    (sea[yCord][xCord + 1] == 1) ^ (sea[yCord][xCord + 1] == 2);
        }
    }

    public static int checkHrz(int xCord, int yCord) {
        //ищем длину корабля
        int count = 0;
        int currCord = xCord;
        //идем влево, пока не упремся либо в границу, либо в не-корабль
        while (xCord - 1 >= 0 && sea[yCord][xCord - 1] == 1 ||
                xCord - 1 >= 0 && sea[yCord][xCord - 1] == 2) {
            xCord--;
            count++;
        }
        xCord = currCord; //возвращаемся на исходную
        //вправо, пока не упремся либо в границу, либо в не-корабль
        while (xCord + 1 < sea[yCord].length && sea[yCord][xCord + 1] == 1 ||
                xCord + 1 < sea[yCord].length && sea[yCord][xCord + 1] == 2) {
            xCord++;
            count++;
        }
        //возвращаем длину
        return count;
    }

    public static int checkVrt(int xCord, int yCord) {
        //ищем длину
        int count = 0;
        int currCord = yCord;
        //вверх, пока не упремся в границу или не-корабль
        while (yCord - 1 >= 0 && sea[yCord - 1][xCord] == 1 ||
                yCord - 1 >= 0 && sea[yCord - 1][xCord] == 2) {
            yCord--;
            count++;
        }
        yCord = currCord; //исходная
        //вниз до упора, либо до не-корабля
        while (yCord + 1 < sea.length && sea[yCord + 1][xCord] == 1 ||
                yCord + 1 < sea.length && sea[yCord + 1][xCord] == 2) {
            yCord++;
            count++;
        }
        return count; //вернем длину
    }

    public static List<Integer> getHShipCords(int xCord, int yCord) {
        //ищем х-координаты частей
        List<Integer> shipCords = new ArrayList<>();
        //влево до упора или не-корабля
        while (xCord - 1 >= 0 && sea[yCord][xCord - 1] == 1 ||
                xCord - 1 >= 0 && sea[yCord][xCord - 1] == 2) {
            xCord--;
        }
        //вправо и добавляем х-координаты в лист
        while (xCord < sea[yCord].length && sea[yCord][xCord] == 1 ||
                xCord < sea[yCord].length && sea[yCord][xCord] == 2) {
            shipCords.add(xCord);
            xCord++;
        }
        return shipCords; //возвращаем лист
    }

    public static List<Integer> getVShipCords(int xCord, int yCord) {
        //ищем у-координаты частей
        List<Integer> shipCords = new ArrayList<>();
        //вверх до упора, либо не-корабля
        while (yCord - 1 >= 0 && sea[yCord - 1][xCord] == 1 ||
                yCord - 1 >= 0 && sea[yCord - 1][xCord] == 2) {
            yCord--;
        }
        //вниз до упора и добавляем в лист у-координаты
        while (yCord < sea.length && sea[yCord][xCord] == 1 ||
                yCord < sea.length && sea[yCord][xCord] == 2) {
            shipCords.add(yCord);
            yCord++;
        }
        return shipCords; //вернем лист
    }

    public static int autoBattle() { //автобой
        int turns = 0; //ходы
        int info; //инфа, во что мы стрельнули
        for (int i = 0; i < sea.length; i++) {
            for (int j = 0; j < sea[i].length; j++) {
                //стреляем подряд во все
                info = fireAt(j + 1, i + 1);
                if (info != -1) { //если попали не в обломки
                    turns++; //увеличиваем ход
                    fireAt(j + 1, i + 1); //стреляем еще раз для проверки
                    turns++; //увеличиваем ход
                }
            }
            /*
            "эффективность" в том, что мы не засчитываем ходы за попадания в обломки
            и мы не выводим поле в консоль лишний раз
            недостаток - если вручную стрелять в обломки - ничего не выведется
             */

        }
        return turns; //вернем ход
    }
}