# Algorithm Analysis 算法分析

## Basic Idea
- **Algorithm 算法：**描述解決问题的具体指令
- **Algorithm Analysis 算法分析：**计算算法需要耗费的`內存大小`與`运行时间`

## Target 算法目标
- **Space Complexity 空間複雜度：**算法需要的`內存大小`
- **Time Complexity 時間複雜度：**算法需要運行的`時間`

## Space Complexity 空間複雜度

+ **组成：**

  1. Instruction Space 程序大小
  2. Data Space 數據大小(算法所需變量、常量空間大小)
  3. Enviorment Stack Space 環境空間(堆、棧使用空間)

+ **分类：**

  1. 固定部分：指令、固定长度的变量、常量储存空间
  2. 可改变部分：变量、动态分配空间、递归栈储存空间
  3. 空间复杂度的的计算主要针对可改变的空间

+ **例子：**

  ```java
  public static float Rsum(float[] a, int n){ 
      if ( n>0 )
          return Rsum(a, n-1) + a[n-1];
      return 0;
  }
  ```

  - 递归栈空间：

    参数：a(2B), n(2B)

    地址：2B

    深度：n+1

    S(n) = 6(n+1)

- **Keys:** 計算方法所使用的變量數量 * 佔用空間

## Time Complexity 時間複雜度
+ **T(p)=compile time+run time**

  1. Compile time 編譯時間
  2. run time 運行時間(operation counts)
  3. 运行时间取决于实例特性(instance characteristics)

+ **平均、最好、最坏复杂度：**

  1. 平均复杂度：把每种情况下的复杂度加起来，然后除以情况的个数，所得的值就是平均复杂度，类似于数学上的均值。
  2. 最好时间复杂度：在最理想的情况下，执行这段代码的时间复杂度。
  3. 最坏时间复杂度：在最不理想，运气最坏的时候，执行这段代码的时间复杂度。
  4. 均摊时间复杂度：绝大部分是低级别的复杂度，个别情况是高级别复杂度。每一次O(n)时间复杂度的操作之后，还有n-1次的O(1)时间复杂度操作。所以我们可以把O(n)时间分摊到O(1)中，这样最终的均摊时间复杂度还是O(1)。

+ **渐进表示法(Asymptotic Notation)(O, &Omega;,&theta;)：**

  1. 形容很大的实例特性的空间与时间复杂度。

  2. 大O表示法(Big Oh Notation(O))： 表示算法上界，f(x)以g(x)为上界，描述算法的最坏复杂度，没有明确下界的时候，使用大O表示法。对所有n, n>=n0，如果f(n)<=cg(n)，则f(n)=O(g(n))。例如：f(n)=3n+2，n>=2、3n+2<=3n+n=4n，故f(n)=O(n)。

     ![大O](./assets/大O)

  3. 大&Omega;表示法(Omega Notation(&Omega;))：表示算法下界，f(x)以g(x)为下界，用与描述算法的最优复杂度，没有明确的上界的时候，使用大Ω表示法。对所有n, n>=n0，如果f(n)>=cg(n)，则f(n)=O(g(n))。

     ![大Omega ](./assets/大Omega)

  4. &theta;表示法(Theta Notation(&theta;))：表示算法确界。g(x)是f(x)的确界，界定函数的渐进上界和渐进下界。当 f(n)= θ(g(n)) 的时候，代表着g(n)为f(n)的渐进紧确界。

     ![大theta](./assets/大theta)

+ **例子**

  ```java
  public static void SelectionSort(int [] a, int){
      //sort the n number in a[0:n-1].
      for(int size=n; size>1; size--){ 
          int j = Max(a,size);
          swap(a[j],a[size-1]);
      }
  }
  ```

  + 分析：

    1. Max(a, size)需要(size - 1)次比较，故总比较数：

       n-1+n-2+……+3+2+1=(n-1)*n/2

    2. 元素移动的次数：

       3(n-1)

  ```java
  public static void InsertionSort( int [ ]a, int n){
      for(int i=0;i<n;i++){ 
          //insert a[i] into a[0:n-1]
          int t=a[i];
          int j;
          for(j=i-1; j>=0&&t<a[j]; j--)
              a[j+1]=a[j];
          a[j+1]=t;
      }
  }
  ```

  + 分析：
    1. 最好：n - 1，移动次数：2(n - 1)
    2. 最坏：(n-1)(n/2) ，移动次数：(1+2)+(2+2)+…..+(n-2+2)+(n-1+2) = n*(n-1)/2+2*(n-1)=(n2+3n-4)/2

  ```java
  public static Comparable Sum( Comparable[] a, int n){ 
      Comparable tsum = 0 ;
      count++;
      for (int i = 0 ; i<n ; i++){ 
          count++;
          tsum += a[i] ;
          count++;
      }
      count++;
      count++; 
      return tsum;
  }
  ```

  + 分析：
    1. 使用全局变量count来计算程序执行的步数。

  ```java
  public static int binarySearch( Comparable [ ] a, Comparable x ){ 
      int low = 0, high = a.length - 1;
      while( low <= high ){ 
          int mid = ( low + high ) / 2;
          if( a[ mid ].compareTo( x ) < 0 )
              low = mid + 1;
          else if( a[ mid ].compareTo( x ) > 0 )
              high = mid – 1;
          else 
              return mid;
      }
      return NOT-FOUND;
  }
  ```

  + 分析：
    1. 最好：1次
    2. 最坏：log_2 n 
    3. 平均：O(log_2 n)

  ```java
  public static int maxSubSum( int [ ] a ){
      return maxSumRec( a, 0, a.length – 1 );
  }
  
  private static int maxSumRec( int [ ] a, int left, int right ){ 
      if ( left = = right )
          if ( a[ left ] > 0 )
              return a[ left ];
      else return 0;
      
      int center = ( left + right ) / 2;
      int maxLeftSum = maxSumRec( a, left, center );
      int maxRightSum = maxSumRec( a, center + 1, right );
      int maxLeftBorderSum = 0, leftBorderSum = 0;
      
      for ( int i = center; i >= left; i-- )
      { 
          leftBorderSum += a[i];
          if ( leftBordersum > maxLeftBorderSum )
              maxLeftBorderSum = leftBorderSum;
      } 
      
      int maxRightBorderSum = 0, rightBorderSum = 0;
      
      for ( int i = center + 1; i <= right; i++ ){ 
          rightBorderSum += a[ i ] ;
          if ( rightBorderSum > maxRightBorderSum )
              maxRightBorderSum = rightBorderSum;
      }
    return max3( maxLeftSum, maxRightSun,
                  maxLeftBorderSum + maxRightBorderSum );
  }
  ```
  
  + 分析：
    1. 分阶段：把问题分成两个大致相等的子问题，然后递归地 对它们求解。 
    2. 治阶段： 将两个子问题的解合并到一起，可能再做些少 量的附加工作，最后得到整个问题的解。
    3. 复杂度：O(n(log n))

- **Keys:** 計算指令行數 * 平均指令執行時間

## 實戰
- **各种复杂度：**
  1. Selection Sort —— O(n^2)
  2. Bubble Sort —— O(n^2)
  3. Rank Sort —— O(n^2)
  4. Sequential Search —— O(n)
  5. Insertion Sort —— O(n^2)
  6.  Binary Search —— O(log_2 n)
  7. MAXIMUM SUBSEQUNCE SUM PROBLEM —— O(n^3) or O(n^2) or O(n(log n))
  8. Euclid’s Algorithm —— O(log n)

- 計算須執行指令行數 N(n) => 計算大O O(N(n))  
ex: 執行 N(n) = n^2 + 5 * n + 1，則複雜度為 O(n^2 + 5 * n + 1) = O(n^2)