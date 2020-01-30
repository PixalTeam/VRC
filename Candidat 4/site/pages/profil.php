<div class="centerho" style="top: 225px;">
<?php
if (isset($_GET['id'])) {
	
	$insertmbr2 = $bdd->prepare("SELECT * FROM users WHERE id = ?");
	$insertmbr2->execute(array($_GET['id']));
	$userinfo = $insertmbr2->fetch();
	$userexist = $insertmbr2->rowCount();
	if ($userexist == 1) {
?>
    <h1><?php echo $userinfo['username'];?></h1><br>
    <h3><?php echo 'Inscrit depuis le '.date('d F Y', $userinfo['date']);?></h3>



	<table class="table table-bordered">
		<tr>
			<th>NOM</th>
			<th>ID</th>
			<th>DATE</th>

		</tr>
		<?php
		$reqvrc = $bdd->prepare("SELECT * FROM vrc WHERE user_id = ? ORDER BY date DESC");
		$reqvrc->execute(array($_GET['id']));
		$vrc = $reqvrc->fetchAll();
		$vrccount = $reqvrc->rowCount();

		for($i=0;$i < $vrccount;$i++) {
			echo'
			<tr>
				<td>'.$vrc[$i]['name'].'</td>
				<td>'.$vrc[$i]['digit'].'</td>
				<td>'.date('d/m/y H:i' ,$vrc[$i]['date']).'</td>
			</tr>';
		}
		?>
	</table> 
</div>
<?php
	} else {
		echo '<h1>L\'utilisateur n\'existe pas</h1>';
	}
} else {
	if (isset($_SESSION['id'])) {
		header('Location: ?page=profil&id='.$_SESSION['id']);
	}
	else {
		echo '<h1>L\'utilisateur n\'existe pas</h1>';
	}
}