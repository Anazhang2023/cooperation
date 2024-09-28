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

    /**
     * 加法
     * @param a
     * @param b
     * @return
     */
    Number add(Number a, Number b){
        a=trans(a);
        b=trans(b);
        return new Number(0,a.up*b.down+a.down*b.up,a.down*b.down);
    }

    /**
     * 减法
     * @param a
     * @param b
     * @return
     */
     Number sub(Number a,Number b){
        a=trans(a);
        b=trans(b);
        return new Number(0,a.up*b.down-a.down*b.up,a.down*b.down);
    }

    /**
     * 乘法
     * @param a
     * @param b
     * @return
     */
    Number mut(Number a,Number b){
        a=trans(a);
        b=trans(b);
        return new Number(0,a.up*b.up,a.down*b.down);
    }

    /**
     * 除法
     * @param a
     * @param b
     * @return
     */
    Number div(Number a,Number b){
        a=trans(a);
        b=trans(b);
        return new Number(0,a.up*b.down,a.down*b.up);
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
        while(number.up<0&&(-number.up)>=number.down){
            fro-=1;
            up+=down;
            if(number.fro<0&&(-number.up)<number.down){
                return  number.fro+"‘"+(-number.up)+"/"+number.down;
            }
        }
        if(number.up==number.down){
            fro+=1;
            up=0;
            down=0;
            return String.valueOf(number.fro);
        }
        if(number.up==0){
            up=0;
            down=0;
            return String.valueOf(number.fro);
        }

        if(fro==0&&number.up<number.down){
            return number.up+"/"+number.down;
        }
            return number.fro+"‘"+number.up+"/"+number.down;
    }
    /**
     * ToNumber--输入格式转换
     */
    public static Number ToNumber(String string) {
        Number number = new Number();
        if (!string.contains("‘")&&!string.contains("/")) {
            number.fro=string.charAt(0)-48;
            number.down=0;
            number.up=0;
        }
        else if(!string.contains("‘")&&string.contains("/")){
            number.fro=0;
            number.up=string.charAt(0)-48;
            number.down=string.charAt(2)-48;
        }
        else if(string.contains("‘")&&string.contains("/")){
            number.fro=string.charAt(0)-48;
            number.up=string.charAt(2)-48;
            number.down=string.charAt(4)-48;
        }
        return number;
    }
    /**
     * 计算真值
     */
    public static double valueOf(String s){
        Number number = ToNumber(s);
        Number trans = number.trans(number);
        if(trans.down!=0){
            return trans.up/trans.down;
        }
        else{
            return 0;
        }
    }
}

