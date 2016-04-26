<?php

function prettifyData($data) {
  $data['price'] = '$' . number_format($data['price'], 2);
  $data['hdd'] = ($data['hdd_size_gb'] < 1000 ? $data['hdd_size_gb'] . 'GB' : $data['hdd_size_gb'] / 1000 . 'TB') . " {$data['hdd_type']}";
  $data['ram_size'] = "{$data['ram_size_gb']}GB";
  $data['screen_size'] = "{$data['screen_size']}&quot;";
  $data['friendly_name'] =
      "{$data['manufacturer']} " .
      "{$data['model_name']} " .
      "{$data['screen_size']} " .
      'Laptop - ' .
      "{$data['processor']} - " .
      "{$data['ram_size']} " .
      "{$data['hdd']}";
  return $data;
}
