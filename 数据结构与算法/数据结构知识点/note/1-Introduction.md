# Introduction

## Main Idea
- **Data 数据：一种信息的载体。**
  1. 数字(numbers)、符号(symbols)、字符(characters)的集合。
  2. 可以用于形容客观的事物。
  3. 這些符号可以被输入进计算机，并被计算机程序辨识与执行。

* **数据两种类型：**
  1. numerical data 数字数据：int, float, complex
  2. non-numerical data 非数字数据：char, string, graph, voice

- **Data Structure 数据结构：**
  1. 一堆数据的集合，同一集合的数据之间有某种关系。
  2. {D, R}：D為数据对象，R表示数据集的关系
* **数据结构两种类型（以连接方式）：**
  1. linear structure 线性结构
  2. non-linear structure 非线性结构：树、图

* **数据结构涉及方面：**
  1. 逻辑结构——抽象数据类型，用户视角(ex: tree)
  2. 物理结构——实现数据类型，具体实现视角(ex: linked list, array)
  3. 相关操作及实现。

## Data Type 数据类型
- **定义：**值(value)与能对值进行计算的操作符的集合。
- **两种类型：**
  1. 原子数据类型——int, float, double……
  2. 结构数据类型——array, struct,……
- **Abstract Data Type ADT 抽象数据类型：**
  1. 是将类型和与这个类型有关的操作集合封装在一起的数据模型。
  2. 将数据类型的使用与它的表示（机内存储）、实现（机内操作的 实现）分开。更确切的说，把一个数据类型的表示及在这个类型上的操作实现封装到一个程序模块中，用户不必知道它。
- **OO Object-Oriented 面向对象：**
  1. 封装、继承、多态
  2. Object 物件：Attribute values(屬性) + Operation(操作)
  3. 根据阿汤哥所说，面向对象的概念其他课会考，这里不专门考。

## Program v.s. Algorithm 程序與算法
- **Program 程序：**
  1. 由可透過计算机执行的语言描述。
  2. 不能满足有限性。
- **Algorithm 算法：**
  1. 描述解決问题的方法。
  2. 可以是多种语言表达。

## Recursion 递归

* **定义：**将算法表示为重复运算的逻辑
  1. Basis cases 基本條件
  2. Induction cases 递归條件

## **Generic Objects 泛型**

* **两个问题：**
  1. 向下转型。(java5以后支持泛型类< AnyType >)
  2. 原始类型 boolean, short, char, byte, int, long, float, double。可使用wrapper来代替。int -> Integer



