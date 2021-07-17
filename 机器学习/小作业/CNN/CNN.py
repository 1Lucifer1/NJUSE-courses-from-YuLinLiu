import tensorflow as tf
from tensorflow import keras
from keras.models import Sequential
from keras.layers import Dense, Dropout, Flatten
from keras.layers import Conv2D, MaxPooling2D
mnist = tf.keras.datasets.mnist

# 一次训练所选取的样本数。
batch_size = 64
# 因为是0，1，2，3，4，5，6，7，8，9一共十个数字
numbers = 10
# 迭代次数
epochs = 20
# 输入的图片是28*28像素
img_rows, img_cols = 28, 28
input_shape = (img_rows, img_cols, 1)

(x_train, y_train), (x_test, y_test) = mnist.load_data()

# channels_last (batch_size,width,height, channels) channels指的是通道数，灰度图是单通道channels=1。
# 图像的输入数据的维度是4维。mnist图像数据只有三维，所以我们要手动把channels这个维度加上。
x_train = x_train.reshape(60000, img_rows, img_cols, 1)
x_test = x_test.reshape(10000, img_rows, img_cols, 1)
x_train = x_train.astype('float32')
x_test = x_test.astype('float32')

# 归一化
x_train /= 255
x_test /= 255
# 将数据转换成2进制
y_train = keras.utils.to_categorical(y_train, numbers)
y_test = keras.utils.to_categorical(y_test, numbers)
model = Sequential()
# 增加一层卷积，常用的滤波器是3*3或5*5的.32个卷积从1个平面抽取特征
model.add(Conv2D(32, kernel_size=(3, 3), activation='relu', input_shape=input_shape))
# 64个卷积从32个平面抽取特征
model.add(Conv2D(64, kernel_size=(3, 3), activation='relu'))
# 池化层采用2*2
model.add(MaxPooling2D(pool_size=(2, 2)))
# 失活一部分神经元，让网络变瘦，同时dropout越小防止过拟合的效果就越好
model.add(Dropout(0.5))
# 拉成一维，扁平化，为了全连接。
model.add(Flatten())
# 全连接
model.add(Dense(128, activation='relu'))
model.add(Dropout(0.5))
# cnn中全连接层后会加上softmax函数
model.add(Dense(numbers, activation='softmax'))
# 查看模型
model.summary()
# cnn一般采用交叉熵函数作为损失函数
model.compile(loss=keras.metrics.categorical_crossentropy, optimizer=keras.optimizers.Adadelta(), metrics=['accuracy'])
model.fit(x_train, y_train, batch_size=batch_size, epochs=epochs, verbose=1, validation_data=(x_test, y_test))

test_loss, test_acc = model.evaluate(x_test, y_test, verbose=0)
print('\nTest loss:', test_loss)
print('\nTest accuracy:', test_acc)

