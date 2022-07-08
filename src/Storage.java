import java.util.Objects;

public class Storage {

    final Product[][][] shelf = new Product[4][3][2];
    String logString;


    public boolean action(Order order, int x, int y, int z) {
        boolean success;
        if (order.isIn()) {
            success = this.in(order, x, y, z);
        } else {
            success = this.out(order, x, y, z);
        }


        return success;
    }

    private boolean in(Order order, int x, int y, int z) {

        System.out.println(order.getProductAttributes());
        if (this.shelf[x][y][z] != null) {
            System.out.println(this.shelf[x][y][z].getAttributes());
        } else {
            System.out.println("empty");
        }
        if (this.shelf[x][y][z] != null) {
            //SLOT IS NOT EMPTY
            return false;
        }
        if (z == 1 && this.shelf[x][y][0] != null && this.shelf[x][y][0] != order.getProduct()) {
            //SLOT IS BLOCKED BY
            return false;
        }

        // SPECIAL CASES FOR TYPE: WOOD
        if (order.getProduct() instanceof Wood) {
            if (Objects.equals(((Wood) order.getProduct()).shape, "beam")) {
                if (this.shelf[x][y][0] != null || this.shelf[x][y][1] != null) {
                    //BEAMS NEED 2 EMPTY SLOTS
                    return false;
                }
                this.shelf[x][y][0] = order.getProduct();
                this.shelf[x][y][1] = order.getProduct();
                return true;
            }
        }

        // SPECIAL CASES FOR TYPE: STONE
        if (order.getProduct() instanceof Stone) {
            if (((Stone) order.getProduct()).weight > 1 && y == 2) {
                //STONES OF MEDIUM WEIGHT CAN NOT BE PLACED IN THE TOP ROW
                return false;
            }
            if (((Stone) order.getProduct()).weight > 2 && y != 0) {
                //STONES OF HEAVY WEIGHT MUST BE PLACED IN THE BOTTOM ROW
                return false;

            }
        }

        this.shelf[x][y][z] = order.getProduct();
        return true;
    }

    private boolean out(Order order, int x, int y, int z) {

        System.out.println(order.getProduct().getAttributes());
        System.out.println(this.shelf[x][y][z].getAttributes());

        if (this.shelf[x][y][z] != null) {
            if (Objects.equals(order.getProduct().getAttributes(), this.shelf[x][y][z].getAttributes())) {

                if (order.getProduct() instanceof Wood) {
                    if (Objects.equals(((Wood) order.getProduct()).shape, "beam")) {
                        this.shelf[x][y][0] = null;
                        this.shelf[x][y][1] = null;

                    }
                }

                if (z == 1 && this.shelf[x][y][0] != null) {
                    logString = "E: ITEM IS BLOCKED BY ANOTHER PALLET";
                    return false;
                }

                this.shelf[x][y][z] = null;

            } else {
                //WRONG PRODUCT SELECTED
                logString = "E: WRONG PRODUCT SELECTED";
                return false;
            }
        } else {
            logString = "E: SHELF IS EMPTY";
        }
        return true;
    }

    public boolean move(int targetX, int targetY, int targetZ,
                        int originX, int originY, int originZ) {


        Order moveOrder = new Order(0, true, this.shelf[originX][originY][originZ], -100);

        if (action(moveOrder, targetX, targetY, targetZ)) {
            this.shelf[originX][originY][originZ] = null;
            if (moveOrder.getProduct() instanceof Wood) {
                if (Objects.equals(((Wood) moveOrder.getProduct()).shape, "beam")) {
                    this.shelf[originX][originY][1] = null;
                    this.shelf[originX][originY][0] = null;
                }
            }
            logString = "S: MOVED: " + moveOrder.getProductAttributes();
            return true;
        } else {
            return false;
        }
//        if (this.shelf[targetX][targetY][targetZ] == null) {
//
//            this.shelf[targetX][targetY][targetZ]=this.shelf[originX][originY][originZ];
//            this.shelf[originX][originY][originZ]=null;
//            success=true;
//        }else{
//            //CANNOT MOVE PALLET TO A SPOT THAT IS ALREADY TAKEN
//            success=false;
//        }
    }

    public boolean scrap(int x, int y, int z) {
        if (this.shelf[x][y][z] != null) {
            logString = "S: SCRAPPED: " + this.shelf[x][y][z].getAttributes();
            if (this.shelf[x][y][z] instanceof Wood) {
                if (Objects.equals(((Wood) this.shelf[x][y][z]).shape, "beam")) {
                    this.shelf[x][y][1] = null;
                    this.shelf[x][y][0] = null;
                }
            }
            if (z == 1 && this.shelf[x][y][0] != null) {
                logString = "E: SLOT IS BLOCKED BY ANOTHER PALLET";
                return false;
            }


            this.shelf[x][y][z] = null;

            return true;


        } else {
            //NOTHING TO SCRAP
            logString = "E: NOTHING TO SCRAP";
            return false;
        }
    }


    public String getLogString() {
        return logString;
    }
}
