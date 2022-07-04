import java.util.Objects;

public class Storage {

        final Product[][][] shelf = new Product[4][3][2];


        public boolean action(Order order, int x, int y, int z){
            boolean success;
           if (order.in){
               success=this.in(order,x,y,z);
           }else{
               success=this.out(order,x,y,z);
           }


            return success;
        }

        private boolean in(Order order, int x, int y, int z){


            if(this.shelf[x][y][z]!=null){
                //SLOT IS NOT EMPTY
                return false;
            }
            if(z==1 && this.shelf[x][y][0]!=null){
                //SLOT IS BLOCKED BY
                return false;
            }
            if(order.product instanceof Wood) {
                if (Objects.equals(((Wood) order.product).type, "beam")) {
                    if (this.shelf[x][y][0] != null || this.shelf[x][y][1] != null) {
                        //BEAMS NEED 2 EMPTY SLOTS
                        return false;
                    }
                    this.shelf[x][y][0] = order.product;
                    this.shelf[x][y][1] = order.product;
                    return true;
                }
            }
            this.shelf[x][y][z]= order.product;

            return true;
        }

    private boolean out(Order order, int x, int y, int z) {

            return true;
    }

    }
