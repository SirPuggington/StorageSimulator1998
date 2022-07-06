import java.util.Objects;

public class Storage {

    final Product[][][] shelf = new Product[4][3][2];



    public boolean action(Order order, int x, int y, int z) {
        boolean success;
        if (order.in) {
            success = this.in(order, x, y, z);
        } else {
            success = this.out(order, x, y, z);
        }


        return success;
    }

    private boolean in(Order order, int x, int y, int z) {

        System.out.println(order.product.getAttributes());
        if(this.shelf[x][y][z]!=null) {
            System.out.println(this.shelf[x][y][z].getAttributes());
        }else {
            System.out.println("empty");
        }
        if (this.shelf[x][y][z] != null) {
            //SLOT IS NOT EMPTY
            return false;
        }
        if (z == 1 && this.shelf[x][y][0] != null) {
            //SLOT IS BLOCKED BY
            return false;
        }

        // SPECIAL CASES FOR TYPE: WOOD
        if (order.product instanceof Wood) {
            if (Objects.equals(((Wood) order.product).shape, "beam")) {
                if (this.shelf[x][y][0] != null || this.shelf[x][y][1] != null) {
                    //BEAMS NEED 2 EMPTY SLOTS
                    return false;
                }
                this.shelf[x][y][0] = order.product;
                this.shelf[x][y][1] = order.product;
                return true;
            }
        }

        // SPECIAL CASES FOR TYPE: STONE
        if (order.product instanceof Stone) {
            if (((Stone) order.product).weight > 1 && y == 2) {
                //STONES OF MEDIUM WEIGHT CAN NOT BE PLACED IN THE TOP ROW
                return false;
            }
            if (((Stone) order.product).weight > 2 && y != 0) {
                //STONES OF HEAVY WEIGHT MUST BE PLACED IN THE BOTTOM ROW
                return false;

            }
        }

        this.shelf[x][y][z] = order.product;
        return true;
    }

    private boolean out(Order order, int x, int y, int z) {

        System.out.println(order.product.getAttributes());
        System.out.println(this.shelf[x][y][z].getAttributes());

        if (this.shelf[x][y][z] != null) {
            if (Objects.equals(order.product.getAttributes(), this.shelf[x][y][z].getAttributes())) {

                if (order.product instanceof Wood) {
                    if (Objects.equals(((Wood) order.product).shape, "beam")) {
                        this.shelf[x][y][0] = null;
                        this.shelf[x][y][1] = null;

                    }}

                if (z == 1 && this.shelf[x][y][0] != null) {
                    //ITEM IS BLOCKED BY ANOTHER PALLET
                    System.out.println("ITEM IS BLOCKED BY ANOTHER PALLET");
                    return false;
                }

                this.shelf[x][y][z] = null;

            } else {
                //WRONG PRODUCT SELECTED
                System.out.println("WRONG PRODUCT SELECTED");
                return false;
            }
        } else {
            System.out.println("SHELF IS EMPTY");
        }

        return true;
    }

    public boolean move(int targetX, int targetY, int targetZ,
                        int originX, int originY, int originZ) {
        boolean success;

        Order moveOrder = new Order(0, true, this.shelf[originX][originY][originZ], -100);

        if (action(moveOrder, targetX, targetY, targetZ)) {
            this.shelf[originX][originY][originZ] = null;
            if (moveOrder.product instanceof Wood) {
                if (Objects.equals(((Wood) moveOrder.product).shape, "beam")) {
                    this.shelf[originX][originY][1] = null;
                }
            }
            success = true;
        } else {
            success = false;
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

        return success;
    }

    public boolean scrap(int x, int y, int z){
        boolean success;

        if(this.shelf[x][y][z]!=null) {
            if (this.shelf[x][y][z] instanceof Wood){
                if (Objects.equals(((Wood) this.shelf[x][y][z]).shape, "beam")){
                    this.shelf[x][y][1] = null;
                    this.shelf[x][y][0] = null;
                }
            }
            else{
                this.shelf[x][y][z] = null;
            }
            success=true;
        }else{
            //NOTHING TO SCRAP
            success=false;
        }
        return success;
    }


}
