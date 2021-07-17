import tensorflow as tf
from tensorflow import keras
mnist = tf.keras.datasets.mnist
# 一次训练所选取的样本数。
batch_size = 64
# 因为是0，1，2，3，4，5，6，7，8，9一共十个数字
numbers = 10
# 迭代次数
epochs = 20

(x_train, y_train), (x_test, y_test) = mnist.load_data()
x_train, x_test = x_train / 255.0, x_test / 255.0
# 该网络的第一层 tf.keras.layers.Flatten 将图像格式从二维数组（28 x 28 像素）转换成一维数组（28 x 28 = 784 像素）。
# 将该层视为图像中未堆叠的像素行并将其排列起来。该层没有要学习的参数，它只会重新格式化数据。
# 展平像素后，网络会包括两个 tf.keras.layers.Dense 层的序列。
# 它们是密集连接或全连接神经层。第一个 Dense 层有 128 个节点（或神经元）。
# 第二个（也是最后一个）层会返回一个长度为 10 的 logits 数组。
# 每个节点都包含一个得分，用来表示当前图像属于 10 个类中的哪一类。
model = tf.keras.models.Sequential([
  tf.keras.layers.Flatten(input_shape=(28, 28)),
  tf.keras.layers.Dense(128, activation='relu'),
  tf.keras.layers.Dropout(0.2),
  tf.keras.layers.Dense(10, activation='softmax')
])
# 在准备对模型进行训练之前，还需要再对其进行一些设置。以下内容是在模型的编译步骤中添加的：
# 损失函数 - 用于测量模型在训练期间的准确率。您会希望最小化此函数，以便将模型“引导”到正确的方向上。
# 优化器 - 决定模型如何根据其看到的数据和自身的损失函数进行更新。
# 指标 - 用于监控训练和测试步骤。以下示例使用了准确率，即被正确分类的图像的比率。
model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
# 训练神经网络模型需要执行以下步骤：
# 1. 将训练数据馈送给模型。在本例中，训练数据位于 train_images 和 train_labels 数组中。
# 2. 模型学习将图像和标签关联起来。
# 3. 要求模型对测试集（在本例中为 test_images 数组）进行预测。
# 4. 验证预测是否与 test_labels 数组中的标签相匹配。
model.fit(x_train, y_train, epochs=epochs, batch_size=batch_size)
test_loss, test_acc = model.evaluate(x_test, y_test, verbose=0)
print('\nTest loss:', test_loss)
print('\nTest accuracy:', test_acc)
