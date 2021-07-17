import TreeDisplay
import id3


def get_data():
    f1 = open("lenses.txt")
    list_row = f1.readlines()
    datas = []

    # 以制表符分割
    for i in range(len(list_row)):
        column_list = list_row[i].strip().split("\t")
        datas.append(column_list)
    # 设置标签
    features = ['age', 'prescript', 'astigmatic', 'tearRate']
    return datas, features


if __name__ == '__main__':
    datas, features = get_data()
    tree = id3.create_tree(datas, features)
    print(tree)
    TreeDisplay.createPlot(tree)
