import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>{
    private Note sentinel;
    private int size;

    public class Note{
        T item;
        Note next;
        Note pre;
    }
    public LinkedListDeque61B(){
        sentinel=new Note();
        sentinel.next=sentinel;
        sentinel.pre=sentinel;
        size=0;
        ind=sentinel;
    }
    @Override
    public void addFirst(Object x) {
        Note nx =new Note();
        nx.item=(T) x;
        nx.next=sentinel.next;
        sentinel.next.pre=nx;

        nx.pre=sentinel;
        sentinel.next=nx;
        this.size++;
    }

    @Override
    public void addLast(Object x) {
        Note nx =new Note();
        nx.item=(T) x;
        nx.pre=sentinel.pre;
        sentinel.pre.next=nx;

        nx.next=sentinel;
        sentinel.pre=nx;
        this.size++;
    }

    @Override
    public List<T> toList() {
        List<T> list=new ArrayList<T>();
        Note p=sentinel.next;
        while(p!=sentinel){
            list.add(p.item);
            p=p.next;
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        int s=0;
        Note p=sentinel.next;
        while(p!=sentinel){
            p=p.next;
            s++;
        }
        return s;
    }

    @Override
    public T removeFirst() {
        if (sentinel.next==sentinel){
            return null;
        }
        Note del=sentinel.next;
        sentinel.next=del.next;
        del.next.pre=sentinel;
        del.next = null; del.pre = null;
        this.size--;
        return del.item;

    }

    @Override
    public T removeLast() {
        if (sentinel.pre==sentinel){
            return null;
        }
        Note del=sentinel.pre;
        sentinel.pre=del.pre;
        del.pre.next=sentinel;
        del.next = null; del.pre = null;
        this.size--;
        return del.item;
    }

    @Override
    public T get(int index) {
        Note n=sentinel;
        while(0<=index&&index<size){
             n=n.next;
             index--;
        }
        return n.item;
    }

    Note ind=sentinel;
    @Override
    public T getRecursive(int index) {
        if(index>=size||index<0){
            ind = sentinel;
            return null;
        }
        ind=ind.next;
        if (index==0){
            T a=ind.item;
            ind=sentinel;
            return a;
        }
        return  getRecursive(index-1);
    }
}
