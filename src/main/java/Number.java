import java.util.Random;

/**
 * @Author : ZhangYiXin
 * @create 2024/9/27 13:14
 */
public class Number {
    int fro=0;
    int up=0;
    int down=0;

    public Number() {
    }

    public Number(int fro, int up, int down) {
        this.fro = fro;
        this.up = up;
        this.down = down;
    }

    Number add(Number a, Number b){
        return new Number(1,1,1);
    }

     Number sub(Number a,Number b){
        a=trans(a);
        b=trans(b);
        return new Number(0,a.up*b.down-a.down*b.up,a.down*b.down);
    }
    /**
     *判断大小
     */
    boolean judge(Number a,Number b){
        a=trans(a);
        b=trans(b);
        if(a.up*b.down-a.down*b.up<=0){
            return false;
        }
        return true;
    }

    Number mut(Number a,Number b){ // 乘法运算
        return new Number(1,1,1);
    }

    Number div(Number a,Number b){  // 除法运算
        return new Number(1,1,1);
    }

    /**
     * 分数转换为真分数
     * @param number
     * @return
     */
    Number trans(Number number){
        if(number.fro!=0&&number.down!=0){
            number.up+=number.fro*number.down;
            number.fro=0;
            return number;
        }
        else if(number.fro!=0&&number.down==0&&number.up==0){
            number.down=1;
            number.up=number.fro;
            number.fro=0;
            return number;
        }
        return number;
    }


    /**
     * toString--输出格式转换
     * @param number
     * @return
     */
    public  String toString(Number number) {
        while (number.up>number.down){
            fro+=1;
            up-=down;
        }
        if(number.up==number.down){
            fro+=1;
            up=0;
            down=0;
            return String.valueOf(number.fro);
        }

        if(fro==0&&number.up<number.down){
            return number.up+"/"+number.down;
        }
            return number.fro+"‘"+number.up+"/"+number.down;
    }
}
