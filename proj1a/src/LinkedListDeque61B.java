import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>{
    Note sentinel;

    public class Note{
        T item;
        Note next;
        Note pre;
    }
    public LinkedListDeque61B(){
        Note sentinel=new Note();
        sentinel.next=sentinel;
        sentinel.pre=sentinel;
    }
    @Override
    public void addFirst(Object x) {
        Note nx =new Note();
        nx.item=(T) x;
        nx.next=sentinel.next;
        sentinel.next.pre=nx;

        nx.pre=sentinel;
        sentinel.next=nx;
    }

    @Override
    public void addLast(Object x) {
        Note nx =new Note();
        nx.item=(T) x;
        nx.pre=sentinel.pre;
        sentinel.pre.next=nx;

        nx.next=sentinel;
        sentinel.pre=nx;
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
        if(sentinel.next==sentinel)return true;
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
    public Object removeFirst() {
        return null;
    }

    @Override
    public Object removeLast() {
        return null;
    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public Object getRecursive(int index) {
        return null;
    }
}
