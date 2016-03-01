__author__ = 'jtakwani'

import numpy as np
import pandas as pd
import statsmodels.api as sm
from statsmodels.tsa.stattools import acf
from statsmodels.tsa.stattools import pacf
from statsmodels.tsa.arima_model import _arma_predict_out_of_sample
import seaborn as sb

sb.set_style('darkgrid')

path = '/Users/jtakwani/PycharmProjects/DataMining/Project/Data/train.csv'
train = pd.read_csv(path, parse_dates = ['Date'], low_memory = False,keep_default_na=False)
store = pd.read_csv("/Users/jtakwani/PycharmProjects/DataMining/Project/Data/store.csv",keep_default_na=False, na_values=0)
test  = pd.read_csv("/Users/jtakwani/PycharmProjects/DataMining/Project/Data/test.csv",parse_dates=['Date'],low_memory=False,keep_default_na=False, na_values=[0])

train = train.sort_values(by='Date')
train = train.set_index('Date')
test = test.sort_values(by='Date')
test = test.set_index('Date')


for i in [1,3,7,8,9,10,11,12,13,14,15,16,19,20,21,22,23,24,25,27,29,30,31,32,33,35,36,38,39,40,41,42,43,
          45,46,47,48,49,50,51,52,53,56,58,61,62,63,64,66,67,68,69,70,71,72,73,74,75,76,77,79,80,81,82,83,
          84,86,89,90,91,92,93,94,98,99,100,101,102,105,107,108,109,110,111,112,113,115,117,118,119,120,122,
          124,126,127,128,129,130,131,132,135,136,137,139,140,141,142,143,144,145,146,147,149,150,152,153,154,
          155,157,158,159,160,161,162,164,165,166,168,169,170,171,172,174,175,176,179,180,181,182,183,184,186,
          187,188,189,190,191,192,193,194,195,197,199,200,201,202,204,206,207,209,210,212,213,214,215,216,217,218,
          219,220,221,224,226,227,228,229,230,231,233,234,235,238,239,241,242,243,244,245,246,247,248,249,250,
          251,252,253,254,255,256,258,259,262,263,265,267,268,269,272,273,274,275,277,278,279,280,281,283,284,
          285,287,288,289,290,294,295,296,297,298,299,300,301,302,303,304,305,306,308,309,310,311,312,314,315,
          316,317,319,320,323,325,326,328,329,330,331,333,334,335,336,337,338,339,340,341,342,343,344,345,346,347,348,350,351,352,353,354,355,356,358,359,362,364,365,367,368,369,370,371,372,373,377,378,379,380,383,385,386,387,388,389,391,392,393,394,395,397,398,399,403,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,420,421,422,424,425,426,427,428,429,430,431,432,433,434,435,440,441,442,445,446,447,448,449,450,451,452,453,455,456,457,458,459,461,463,465,466,467,468,470,471,472,473,475,477,481,484,485,486,487,488,490,491,492,493,495,497,498,499,500,501,502,504,505,506,507,508,509,510,511,512,514,515,516,517,518,519,520,521,522,524,527,528,529,530,531,532,533,534,535,536,537,538,539,540,541,542,543,545,547,548,549,550,551,552,553,554,555,557,558,561,562,563,564,565,566,567,568,570,571,572,573,574,575,577,578,579,580,581,582,584,585,586,587,588,589,590,591,592,593,597,598,600,601,602,603,604,605,610,611,612,615,616,618,619,620,621,622,623,624,625,627,628,629,631,632,633,636,637,638,639,640,641,642,643,644,645,646,647,650,651,653,655,656,657,658,659,660,661,662,663,665,666,667,669,670,671,673,674,675,676,677,678,680,681,684,685,687,689,690,691,692,693,694,695,696,697,699,700,701,702,703,705,706,707,710,711,712,713,714,716,717,718,719,720,721,722,723,724,725,727,728,729,731,732,733,734,736,737,738,739,740,741,742,744,746,748,749,750,751,752,753,756,757,758,759,762,763,764,765,766,767,768,769,770,771,772,773,774,775,776,777,778,782,784,785,789,790,791,792,793,795,796,797,799,800,801,802,803,804,805,806,807,809,810,811,813,815,816,818,819,820,822,823,824,825,826,829,831,832,833,835,837,840,842,844,845,846,847,848,849,850,851,852,853,855,856,857,858,859,860,861,862,863,864,865,866,867,868,871,872,874,875,877,879,880,881,882,883,884,885,886,887,888,890,891,893,894,895,896,897,900,901,902,903,904,905,906,907,908,909,911,912,913,914,915,916,917,919,920,922,924,925,926,927,928,929,930,931,932,934,935,936,937,938,939,941,942,943,944,945,946,947,948,950,951,952,954,955,956,960,961,962,964,965,966,967,969,970,973,974,975,976,977,980,983,984,985,986,988,989,991,992,994,997,998,1000,1003,1004,1005,1007,1008,1009,1010,1011,1012,1013,1014,1015,1016,1019,1020,1022,1024,1025,1026,1027,1028,1031,1036,1037,1038,1039,1040,1041,1042,1044,1045,
          1047,1048,1049,1050,1051,1052,1053,1054,1056,1057,1058,1059,1060,1061,1062,1063,1064,1065,1066,1067,1068,1070,1071,1072,1073,1076,1077,1078,1079,1080,1083,1084,1086,1087,1088,1089,1091,1092,1094,1096,1097,1099,1100,1101,1102,1103,1104,1105,1106,1107,1109,1111,1112,1113,1114,1115]:
    store_id = i

    store_data = train[train["Store"] == store_id]
    means = train.groupby([ 'Store', 'DayOfWeek', 'Promo' ])['Sales'].mean()
    test_data = test.groupby(["Store","Promo"]).get_group((1,1))
    lag_correlations = acf(store_data['Sales'].iloc[1:])
    lag_partial_correlations = pacf(store_data['Sales'].iloc[ 1:])

    model = sm.tsa.ARIMA(store_data["Sales"].iloc[1:], order=(7,0,2))
    result = model.fit()
    params = result.params
    residuals = result.resid
    p = result.k_ar
    q = result.k_ma
    k_exog = result.k_exog
    k_trend = result.k_trend
    steps = 48
    store_data['Forecast'] = result.fittedvalues
    store_data[['Sales', 'Forecast']].plot(figsize=(16, 12))
    values = _arma_predict_out_of_sample(params, steps, residuals, p, q, k_trend, k_exog, endog=store_data["Sales"], exog=None, start=len(store_data))
    print i
    print values
    res = []
    with open("Out.txt",'a') as file_handle:
        np.savetxt(file_handle, values , fmt="%f")
