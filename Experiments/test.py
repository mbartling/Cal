# -*- coding: utf-8 -*-
"""
Created on Tue Oct 14 16:43:28 2014

@author: michael
"""

from sklearn import svm
from sklearn import preprocessing

import numpy as np
import matplotlib.pyplot as plt
from matplotlib.colors import ListedColormap

#data = np.genfromtxt('myAccelData/TempAccelData_10-15-2014_13-49-27.csv', delimiter=',' , usecols=(0,1,2))
#state = np.genfromtxt('myAccelData/TempAccelData_10-15-2014_13-49-27.csv', dtype='int', delimiter=',' , usecols=(3))
data = np.genfromtxt('someData2.csv', delimiter=',' , usecols=(0,1,2))
state = np.genfromtxt('someData2.csv', dtype='int', delimiter=',' , usecols=(3))

print data
print state

clf = svm.SVC(kernel='rbf', gamma=30, C=100, probability=True, tol=0.01)
data_scaled = preprocessing.scale(data)
res = clf.fit(data, state)
score = clf.score(data, state)

print res

print clf.predict([-0.388885, 0.111725, 9.558594])

# just plot the dataset first
cm = plt.cm.RdBu
cm_bright = ListedColormap(['#FF0000', '#0000FF'])

x_min, x_max = data[:, 0].min() - .5, data[:, 0].max() + .5
y_min, y_max = data[:, 1].min() - .5, data[:, 1].max() + .5
z_min, z_max = data[:, 2].min() - .5, data[:, 2].max() + .5

#xx, yy, zz = np.meshgrid(np.arange(x_min, x_max, h),
#                         np.arange(y_min, y_max, h),np.arange(z_min, z_max, h))

plt.close('all')
plt.figure()
ax = plt.subplot(1,1,1)
ax.scatter(data[:,0], data[:,1], c='red')

# Plot the decision boundary. For that, we will assign a color to each
# point in the mesh [x_min, m_max]x[y_min, y_max].
#if hasattr(clf, "decision_function"):
#Z = clf.decision_function(np.c_[xx.ravel(), yy.ravel(), zz.ravel()])
#else:
#    Z = clf.predict_proba(np.c_[xx.ravel(), yy.ravel()])[:, 1]

# Put the result into a color plot
#Z = Z.reshape(xx.shape)
#ax.contourf(xx, yy, Z, cmap=cm, alpha=.8)
#plt.show()


#data = np.genfromtxt('myAccelData/TempAccelData_10-15-2014_13-50-29.csv', delimiter=',' , usecols=(0,1,2))
#state = np.genfromtxt('myAccelData/TempAccelData_10-15-2014_13-50-29.csv', dtype='int', delimiter=',' , usecols=(3))
data = np.genfromtxt('someData3.csv', delimiter=',' , usecols=(0,1,2))
state = np.genfromtxt('someData3.csv', dtype='int', delimiter=',' , usecols=(3))
#data = np.genfromtxt('myAccelData/TempAccelData_10-15-2014_13-08-01.csv', delimiter=',' , usecols=(0,1,2))
#state = np.genfromtxt('myAccelData/TempAccelData_10-15-2014_13-08-01.csv', dtype='int', delimiter=',' , usecols=(3))

plt.figure();
plt.subplot(2,1,1)
plt.stem(state)

plt.subplot(2,1,2)
data_scaled = preprocessing.scale(data)
plt.stem(clf.predict(data))
ax.scatter(data[:,0], data[:,1], c='blue')

plt.show();

score1 = clf.score(data, state)
print score
print score1
