import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BPreconditionTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }
    @Test
    void addtest(){
        Deque61B a=new ArrayDeque61B<Integer>();
        for(int i=0;i<8;i++) {
            a.addFirst(i);
            a.addLast(-i);
        }

    }
    @Test
    void largegettest(){
        Deque61B a=new ArrayDeque61B<Integer>();
        for(int i=0;i<8;i++) {
            a.addFirst(i);
        }
        for(int i=0;i<8;i++) {
            a.addLast(-i);
        }
        Object x=a.get(8);
        assertThat(a.get(959)).isNull();
        assertThat(a.get(5)).isEqualTo(2);
        assertThat(a.get(8)).isEqualTo(0);
        assertThat(a.get(15)).isEqualTo(-7);
    }

    @Test
    void smallgettest(){
        Deque61B a=new ArrayDeque61B<Integer>();
        for(int i=0;i<8;i++) {
            a.addFirst(i);
        }
        assertThat(a.get(959)).isNull();
        assertThat(a.get(5)).isEqualTo(2);
    }
    @Test
    void Test(){
        Deque61B a=new ArrayDeque61B<Integer>();
        for(int i=0;i<8;i++) {
            a.addFirst(i);
        }
        for(int i=0;i<8;i++) {
            a.addLast(-i);
        }
        System.out.println(a.toList());
    }



        // 1. 测试空队列的基础操作（isEmpty、size、remove、get）
        @Test
        void testEmptyDequeOperations() {
            Deque61B<Integer> a = new ArrayDeque61B<>();
            // 验证空队列状态
            assertThat(a.isEmpty()).isTrue();
            assertThat(a.size()).isEqualTo(0);
            // 空队列remove返回null
            assertThat(a.removeFirst()).isNull();
            assertThat(a.removeLast()).isNull();
            // 空队列get任意索引返回null
            assertThat(a.get(-1)).isNull();
            assertThat(a.get(0)).isNull();
            assertThat(a.get(100)).isNull();
            // 空队列toList为空
            assertThat(a.toList()).isEmpty();
        }

        // 2. 测试单元素队列的增删查
        @Test
        void testSingleElementDeque() {
            Deque61B<Integer> a = new ArrayDeque61B<>();
//            // 添加单个元素（addFirst）
//            System.out.println("1");
            a.addFirst(5);
//            assertThat(a.isEmpty()).isFalse();
//            assertThat(a.size()).isEqualTo(1);
//            assertThat(a.get(0)).isEqualTo(5);
            // 移除唯一元素（removeFirst）
            System.out.println("2");
            assertThat(a.removeFirst()).isEqualTo(5);
            assertThat(a.isEmpty()).isTrue();
            assertThat(a.size()).isEqualTo(0);
            System.out.println("3");
            // 重新添加单个元素（addLast）
            a.addLast(8);
            assertThat(a.get(0)).isEqualTo(8);
            System.out.println("4");
            // 移除唯一元素（removeLast）
            assertThat(a.removeLast()).isEqualTo(8);
            assertThat(a.size()).isEqualTo(0);
            System.out.println("5");
        }

        // 3. 测试addFirst/addLast基础功能（非扩容场景）
        @Test
        void testAddFirstAddLastBasic() {
            Deque61B<Integer> a = new ArrayDeque61B<>();
            // 交替addFirst/addLast
            a.addFirst(3);   // [3]
            a.addLast(5);    // [3,5]
            a.addFirst(1);   // [1,3,5]
            a.addLast(7);    // [1,3,5,7]
            // 验证size和元素
            assertThat(a.size()).isEqualTo(4);
            assertThat(a.get(0)).isEqualTo(1);
            assertThat(a.get(1)).isEqualTo(3);
            assertThat(a.get(2)).isEqualTo(5);
            assertThat(a.get(3)).isEqualTo(7);
            // 无效索引get返回null
            assertThat(a.get(4)).isNull();
            assertThat(a.get(-2)).isNull();
        }

        // 4. 测试扩容（resize）功能（超过初始容量8）
        @Test
        void testResizeWhenFull() {
            Deque61B<Integer> a = new ArrayDeque61B<>();
            // 初始容量8，添加9个元素触发扩容
            for (int i = 0; i < 9; i++) {
                a.addLast(i);
            }
            System.out.println(a.toList());
            // 验证扩容后size和元素正确性
            assertThat(a.size()).isEqualTo(9);
            for (int i = 0; i < 9; i++) {
                assertThat(a.get(i)).isEqualTo(i);
            }
            // 继续添加元素，验证扩容后仍可正常添加
            a.addFirst(-1);
            assertThat(a.get(0)).isEqualTo(-1);
            assertThat(a.get(9)).isEqualTo(8);
            assertThat(a.size()).isEqualTo(10);
        }

        // 5. 测试缩容（resizedown）功能（满足size>=16且负载因子<0.25）
        @Test
        void testResizedownWhenSparse() {
            Deque61B<Integer> a = new ArrayDeque61B<>();
            // 先添加16个元素（触发扩容到16）
            for (int i = 0; i < 16; i++) {
                a.addLast(i);
            }
            assertThat(a.size()).isEqualTo(16);
            // 移除12个元素，剩余4个（4/16=0.25，不满足<0.25；移除13个，剩余3，3/16=0.1875<0.25）
            for (int i = 0; i < 13; i++) {
                a.removeFirst();
            }
            assertThat(a.size()).isEqualTo(3);
            // 触发缩容后，验证元素仍可正确访问
            assertThat(a.get(0)).isEqualTo(13);
            assertThat(a.get(1)).isEqualTo(14);
            assertThat(a.get(2)).isEqualTo(15);
            // 继续操作验证缩容后功能正常
            a.addFirst(12);
            assertThat(a.get(0)).isEqualTo(12);
            assertThat(a.size()).isEqualTo(4);
        }

        // 6. 测试get方法的边界和环形场景
        @Test
        void testGetMappingRingScenario() {
            Deque61B<Integer> a = new ArrayDeque61B<>();
            // 先addFirst填满前半部分，触发环形
            for (int i = 0; i < 8; i++) {
                a.addFirst(i);  // first最终指向7（初始size=8）
            }
            // 再addLast扩展到环形区域
            for (int i = 0; i < 8; i++) {
                a.addLast(-i);
            }
            // 验证环形场景下get的正确性（覆盖你原测试的largegettest/smallgettest）
            assertThat(a.get(959)).isNull();
            assertThat(a.get(5)).isEqualTo(2);
            assertThat(a.get(8)).isEqualTo(0);
            assertThat(a.get(15)).isEqualTo(-7);
            // 验证边界索引
            assertThat(a.get(0)).isEqualTo(7);
            assertThat(a.get(15)).isEqualTo(-7);
            assertThat(a.get(16)).isNull();
        }

        // 7. 测试removeFirst/removeLast的环形逻辑和边界
        @Test
        void testRemoveOperationsRing() {
            Deque61B<Integer> a = new ArrayDeque61B<>();
            // 构造环形队列：addFirst 8次（first=7），addLast 2次（last=1）
            for (int i = 0; i < 8; i++) {
                a.addFirst(i);
            }
            a.addLast(-1);
            a.addLast(-2);
            assertThat(a.size()).isEqualTo(10);

            // 测试removeFirst（环形跳转）
            assertThat(a.removeFirst()).isEqualTo(7);
            assertThat(a.removeFirst()).isEqualTo(6);
            assertThat(a.get(0)).isEqualTo(5);
            // 测试removeLast（边界）
            assertThat(a.removeLast()).isEqualTo(-2);
            assertThat(a.removeLast()).isEqualTo(-1);
            assertThat(a.get(a.size() - 1)).isEqualTo(0);
            // 连续remove到空
            while (a.size() > 0) {
                a.removeFirst();
            }
            assertThat(a.isEmpty()).isTrue();
            assertThat(a.removeLast()).isNull();
        }

        // 8. 测试toList方法的正确性
        @Test
        void testToListMethod() {
            Deque61B<Integer> a = new ArrayDeque61B<>();
            // 构造队列：[1,3,5,7,9]
            a.addFirst(3);
            a.addFirst(1);
            a.addLast(5);
            a.addLast(7);
            a.addLast(9);
            // 验证toList的顺序和内容
            List<Integer> list = a.toList();
            assertThat(list).hasSize(5);
            assertThat(list).containsExactly(1, 3, 5, 7, 9);
            // 空队列toList
            Deque61B<Integer> emptyDeque = new ArrayDeque61B<>();
            assertThat(emptyDeque.toList()).isEmpty();
        }


        // 10. 测试连续add/remove后的状态一致性
        @Test
        void testContinuousAddRemoveConsistency() {
            Deque61B<Integer> a = new ArrayDeque61B<>();
            // 连续交替add/remove
            for (int i = 0; i < 20; i++) {
                if (i % 2 == 0) {
                    a.addFirst(i);
                } else {
                    a.addLast(i);
                }
            }
            assertThat(a.size()).isEqualTo(20);
            // 移除前10个元素
            for (int i = 0; i < 10; i++) {
                a.removeFirst();
            }
            assertThat(a.size()).isEqualTo(10);
            // 验证剩余元素正确性
            assertThat(a.get(0)).isEqualTo(18);  // 最后一次addFirst是18（i=18偶数）
            assertThat(a.get(9)).isEqualTo(19); // 最后一次addLast是19（i=19奇数）
            // 继续移除到只剩1个
            for (int i = 0; i < 9; i++) {
                a.removeLast();
            }
            assertThat(a.size()).isEqualTo(1);
            assertThat(a.get(0)).isEqualTo(18);
        }
    }

