from math import log
import operator


def calculate_shannon(data_set):
    data_set_len = len(data_set)
    label_counts = {}
    for feature_vec in data_set:
        # 提取soft hard还是不适合的标签
        label = feature_vec[-1]
        if label not in label_counts.keys():
            label_counts[label] = 0
        label_counts[label] += 1
    # 信息熵
    entropy = 0.0
    for key in label_counts.keys():
        # 这个就是每种决策结果（soft,hard还是no lenses占数据集的比重，也就是p）
        part_ent = float(label_counts[key] / data_set_len)
        # 将结果累加，得到根节点或该属性取值的信息熵
        entropy -= part_ent * log(part_ent, 2)
    return entropy


def split_data(data_set, feature_index, value):
    data_split = []  # 划分后的数据集
    for feature in data_set:
        if feature[feature_index] == value:
            reFeature = feature[:feature_index]
            reFeature.extend(feature[feature_index + 1:])
            data_split.append(reFeature)
    return data_split


def best_feature(data_set):
    feature_num = len(data_set[0]) - 1
    # 计算根节点的信息熵
    root_env = calculate_shannon(data_set)
    info_increase = float(0)
    feature_pos = -1
    # 每列属性
    for i in range(feature_num):
        features = []
        # 同一列的相同属性
        for fe_choose in data_set:
            features.append(fe_choose[i])
        # 取值的集合
        unique_feature = set(features)
        # 信息熵
        entropy = 0.0
        for value in unique_feature:
            sub_data = split_data(data_set, i, value)
            # 计算该种可能性出现的概率
            probability = len(sub_data) / float(len(data_set))
            # 用这个概率去乘以子集的香农熵
            entropy += probability * calculate_shannon(sub_data)

        info_gain = root_env - entropy
        if info_gain > info_increase:
            info_increase = info_gain
            feature_pos = i
    return feature_pos


def main_feat(features):
    values = {}
    for value in features:
        if value not in values.keys():
            values[value] = 0
        values += 1
    sortedValues = sorted(values.items(), key=operator.itemgetter(1), reverse=True)
    # 返回出现次数最多的
    return sortedValues[0][0]


def create_tree(data_set, labels):
    # 决策结果的集合
    classList = [example[-1] for example in data_set]

    # 当类别与属性完全相同时停止，这里的classList[0]就是no lenses,soft 和hard
    if classList.count(classList[0]) == len(classList):
        # 返回标签。
        return classList[0]
    # 遍历完所有特征值时，返回数量最多的
    if len(data_set[0]) == 1:
        return main_feat(classList)

    # 获取最佳划分属性的Index，以0开始
    bestFeat = best_feature(data_set)
    bestFeatLabel = labels[bestFeat]
    my_tree = {bestFeatLabel: {}}
    # 清空labels中的该列
    del (labels[bestFeat])
    # 获取该列中所有的属性值
    featValues = [example[bestFeat] for example in data_set]
    # 唯一化
    uniqueVals = set(featValues)

    for value in uniqueVals:
        # 获取剩下的属性
        subLabels = labels[:]
        # 递归调用创建决策树
        my_tree[bestFeatLabel][value] = create_tree(split_data(data_set, bestFeat, value), subLabels)
    return my_tree
