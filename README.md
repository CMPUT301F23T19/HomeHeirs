# CMPUT 301 F2023 Team 19 — HomeHeirs

Welcome to HomeHeirs! This is a collaborative project to design and implement an Android application.

## Overview

Our application allows a user to easily record and manage their household inventory (for purposes like insurance), and store this information.

## Building locally

To work locally with this project on Android Studio, follow these steps below:

### Install

Clone the repository into a folder of your choice.

```bash
git clone https://github.com/CMPUT301F23T19/HomeHeirs.git
```

### Execute

When editing files, and you want to commit changes to the repository, follow these steps below:

1. Open Android Studio
2. Make the edits on the files you wish to make
3. Review the changes you made
4. Once you are satistfied with your changes, save the changes by **committing** them to the _main branch_ of the repository
```
git checkout (your_branch_name) —— this ensures that you are doing edits only on your own branch, and not the main
git add --all —— if you are in the root folder (HomeHeirs), this ensures that all files that have been edited can be pushed to the main branch
git commit -m "<insert brief descrption of what has been revised here>"
git push --set-upstream origin (your_branch_name) —— under the "Pull requests" tab, create your pull request, and ask another member to accept it
```

If you would like to pull changes from the main branch into your own branch, follow these steps below:
```
git checkout (your_branch_name) —— this ensure that you are in your own branch, and not the main
git pull origin main —— this command fetches changes from the main branch and automatically merges them into your own branch
```
