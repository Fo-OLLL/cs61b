import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T>{
    private int first=-1;
    private int last=-1;
    private int size=8;
    private T[] a=(T[])new Object[size];
    private int num=0;

    public ArrayDeque61B(){

    }

    private void resize(){
        T[] a2=(T[])new Object[size*2];
        for(int i=0;i<num;i++){
            a2[i]=this.get(i);
        }
        a=a2;
        first=0;
        last=num-1;
        size*=2;
    }
    private void resizedown(){
        if(size>=16 && (double)num/size<0.25){
            if(num==0)return;
            T[] a2=(T[])new Object[size/2];
            for(int i=0;i<num;i++){
                a2[i]=this.get(i);
            }
            a=a2;
            first=0;
            last=num-1;
            size/=2;
        }
    }

    @Override
    public void addFirst(T x) {
        if(num==size){
            resize();
        }
        if(this.first==-1){
            a[0]=x;
            last=0;
            first=0;
        }
        else if (first==0){
            a[size-1]=x;
            first=size-1;
        }
        else{
            a[first-1]=x;
            first--;
        }
        num++;
    }

    @Override
    public void addLast(T x) {
        if(num==size){
            resize();
        }
        num++;
        if(this.last==-1){
            a[0]=x;
            last=0;
            first=0;
        }
        else
        {
            if(last==size-1){
                last=0;
                a[last]=x;

                return;
            }
            last++;
            a[last]=x;

        }

    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for(int i=0;i<num;i++){
            returnList.add(this.get(i));
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return num==0;
    }

    @Override
    public int size() {
        return num;
    }

    @Override
    public T removeFirst() {
        resizedown();
        if(num==0){
            return null;
        }
        T st=this.get(0);
        if (num==1) {//只有一个元素
            first=-1;
            last=-1;
        }
        else{
            first++;
        }

        if(first>=size){
            first-=size;
        }
        num--;
        return st;
    }

    @Override
    public T removeLast() {
        resizedown();
        if(num==0){
            return null;
        }
        T st=this.get(num-1);
        if (num==1) {//只有一个元素
            first=-1;
            last=-1;
            num--;
            return st;
        }
        else{
            last--;
        }

        if(last<0){
            last+=size;
        }
        num--;
        return st;

    }

    @Override
    public T get(int index) {
        if(-1<index&&index<num){
            if(size-1-first<index){
                return a[index-size+first];
            }
            else{
                return a[first+index];
            }
        }
        else{
            return null;
        }
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
