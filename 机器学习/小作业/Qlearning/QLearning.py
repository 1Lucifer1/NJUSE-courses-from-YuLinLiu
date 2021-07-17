import numpy as np
import pandas as pd
import time

# 固定随机数
np.random.seed(4)

states = 10  # 一共0-9状态
actions = ["left", "right"]  # action只有两个左和右
e = 0.2  # 贪心策略
u = 0.2  # 学习率
y = 0.8  # 折扣率
circles = 12  # 一共训练12次
refresh = 0.1


def choose_action(state, q_table):
    state_action = q_table.iloc[state, :]
    # 等于零就随便选一条路走
    if (np.random.uniform() < e) or (state_action.all() == 0):
        action_choose = np.random.choice(actions)
    else:
        action_choose = state_action.idxmax()
    return action_choose


def get_state_reward(s, A):
    if A == "right":
        if s == states - 2:
            s2 = "terminal"
            reward = 1
        else:
            s2 = s + 1
            # 保证反比，
            reward = pow(1 / (states - s2), 5)
    else:
        # 这个是说到头了 不能再后退了
        if s == 0:
            s2 = s
        else:
            s2 = s - 1
        reward = pow(1 / (states - s2), 5)
    return s2, reward


def update_env(S, episode, step_count):
    env_list = ["-"] * (states - 1) + ["T"]

    if S == "terminal":
        interaction = "Episode %s: total_steps = %s" % (episode + 1, step_count)
        print("\r{}".format(interaction), end='')
        time.sleep(2)
        print('\r                        ', end='')
    else:
        env_list[S] = '0'
        interaction = ''.join(env_list)
        print("\r{}".format(interaction), end='')
        time.sleep(refresh)


def q_learning():
    # 先初始化一个Q table
    q_table = pd.DataFrame(np.zeros((states, len(actions))), columns=actions)
    for episode in range(circles):
        step_count = 0
        # 选择一个初始的S
        S = 0
        is_terminal = False
        update_env(S, episode, step_count)
        # 如果S不是终止状态的话，选择动作，得到环境给出的一个反馈S_(新的状态)和R(奖励)
        while not is_terminal:
            A = choose_action(S, q_table)
            S2, R = get_state_reward(S, A)
            q_current = q_table.loc[:S, A]
            if S2 != "terminal":
                # 算出来实际的Q值
                q_next = R + y * q_table.iloc[S2, :].max()
            else:
                q_next = R
                is_terminal = True
            q_table.loc[:S, A] += u * (q_next - q_current)
            S = S2
            update_env(S, episode, step_count + 1)
            step_count = step_count + 1
    return q_table


if __name__ == "__main__":
    q_table = q_learning()
    print("\r\nQ-table:\n")
    print(q_table)
