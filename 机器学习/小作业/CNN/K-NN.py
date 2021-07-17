import operator
import os
import numpy as np
import struct
import datetime
from multiprocessing import Process,Manager,Lock


# 方法：读取数据集中储存图片的文件，返回一个大的矩阵，每个行向量代表一张图片（28*28像素）
def load_image(file_name):
    # 读取整个文件进入缓冲区
    file_content = open(file_name,"rb").read()
    # 读取文件头信息，共有16个字节,从offset=0开始读取
    magic_number, num_images, num_rows, num_cols = struct.unpack_from('>IIII', file_content, 0)
    offset = struct.calcsize('>IIII')
    # 读取之后的图片信息
    image_size = num_rows * num_cols
    fmt_image = '>'+str(image_size)+'B'  # '>'表示大端，'B'表示integer(1个字节)
    images = np.empty((num_images,image_size)) # 新建一个大的矩阵存放图片信息
    for i in range(num_images):
        images[i] = np.array(struct.unpack_from(fmt_image, file_content, offset))
        offset += struct.calcsize(fmt_image)
    return images


# 方法：读取与图片相对应的储存标签的文件，返回一个一维数组，数组的元素值即为对应图片的数字值
def load_label(file_name):
    # 读取整个文件进入缓冲区
    file_content = open(file_name,"rb").read()
    # 读取文件头信息，共有8个字节,从offset=0开始读取
    magic_number, labels_num = struct.unpack_from('>II', file_content, 0)
    offset = struct.calcsize('>II')
    # 读取之后的标签信息
    fmt_label = '>'+str(labels_num)+'B'
    labels = np.array(struct.unpack_from(fmt_label, file_content, offset))
    return labels


# 方法：保存计算过程中出现的预测值与真实值不匹配的错误样本，把错误的测试样本编号记录下来
def save_error(error_test_count, error_lock, errors_list):
    with error_lock:# 因为errors_list是存放在进程公共通信区的，所以需要加把锁
        errors_list.append(error_test_count)

# 方法：K近邻算法（KNN）得到测试图片的预测值，同时为了适配多进程，做了一些调整。
def KNN(test_image,test_label,train_images,train_labels,k,process_index,test_number,process_number,error_lock,errors_list):
    for test_count in range(process_index,test_number,process_number):
        # 读取训练集的行数,即训练集有多少张图片
        train_images_num = train_images.shape[0]
        # 求距离：先tile函数将测试集拓展成与训练集相同维数的矩阵，计算测试样本与每一个训练样本的欧式距离
        # 通过欧式距离的大小来判断图片的相似度
        all_distances = (np.sum((np.tile(test_image[test_count],(train_images_num,1))-train_images)**2,axis=1))**0.5
        # 按all_distances中元素进行升序排序后得到其对应索引的列表
        sorted_distance_index = all_distances.argsort()
        # 选择距离最小的k个样本，看一下它们中大部分都是哪个数字的样本
        classCount = np.zeros((10),dtype=int) 
        # 10代表有10个数字，元素值表示对应数字的样本在这k个样本中出现了几次
        for i in range(k):
            vote_label = train_labels[sorted_distance_index[i]]
            classCount[vote_label] += 1
        # 找出出现最多的数字样本，为预测值
        result_label = -1
        max_times = 0
        for i in range(10):
            if classCount[i] >= max_times:
                max_times = classCount[i]
                result_label = i
        print ("进程", process_index+1,":第",test_count+1,"张测试图片：","预测值:",result_label,"真实值:",test_label[test_count],end='')
        if (result_label != test_label[test_count]):
            save_error(test_count,error_lock,errors_list)
            print('…………（错误！）',end='')
        print(' ')


# 程序入口
def main():
    t1 = datetime.datetime.now()  # 计时开始
    k = int(input('选取最邻近的K个值，K='))
    test_number = int(input('输入需要测试的样本数(1-10000):'))
    process_number = int(input('输入计算进程数（最好与CPU核心数相同）:'))
    # 载入文件
    train_image = load_image('MNIST_data\\train-images.idx3-ubyte')
    train_label = load_label('MNIST_data\\train-labels.idx1-ubyte')
    test_image = load_image('MNIST_data\\t10k-images.idx3-ubyte')
    test_label = load_label('MNIST_data\\t10k-labels.idx1-ubyte')
    # 设置进程共享区域，访问锁
    errors = Manager() 
    error_lock = Lock()
    errors_list = errors.list([])
    # 批量新建和开始进程
    process_list = []
    for i in range(process_number):
        p=Process(target=KNN,args=(test_image,test_label,train_image,train_label,k,i,test_number,process_number,error_lock,errors_list))
        p.start()
        process_list.append(p)
    for p in process_list:
        p.join()
    # 测试样本预测完毕，打印计算结果
    print ("\nk值为:",k)
    print ("测试样本数为:",test_number)
    print ("计算进程数为:",process_number)
    print ("测试出错数为:",len(errors_list))
    print ("\n错误率 ={:.2f}%".format(len(errors_list)/float(test_number)*100))
    t2 = datetime.datetime.now()
    print('耗 时 = ', t2 - t1)


if __name__ == "__main__":
    main()
